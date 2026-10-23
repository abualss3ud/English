package com.example.data.repository

import com.example.data.dataset.A1VocabularyDataset
import com.example.data.local.AppDatabase
import com.example.data.local.DailyLessonEntity
import com.example.data.local.UserSettingsEntity
import com.example.data.local.WordProgressEntity
import com.example.data.model.VocabularyWord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LearningRepository(private val database: AppDatabase) {
    private val wordProgressDao = database.wordProgressDao()
    private val dailyLessonDao = database.dailyLessonDao()
    private val userSettingsDao = database.userSettingsDao()

    val allProgressFlow: Flow<List<WordProgressEntity>> = wordProgressDao.getAllProgress()
    val allLessonsFlow: Flow<List<DailyLessonEntity>> = dailyLessonDao.getAllLessons()
    val settingsFlow: Flow<UserSettingsEntity?> = userSettingsDao.getSettingsFlow()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    fun getTodayDate(): String = dateFormat.format(Date())

    fun getAllWords(): List<VocabularyWord> = A1VocabularyDataset.words

    fun getWordById(id: Int): VocabularyWord? = A1VocabularyDataset.words.find { it.id == id }

    suspend fun getOrCreateSettings(): UserSettingsEntity {
        val existing = userSettingsDao.getSettings()
        if (existing != null) return existing
        val defaultSettings = UserSettingsEntity()
        userSettingsDao.saveSettings(defaultSettings)
        return defaultSettings
    }

    suspend fun updateSettings(settings: UserSettingsEntity) {
        userSettingsDao.saveSettings(settings)
    }

    fun getWordsForDay(dayNumber: Int): List<VocabularyWord> {
        val dayWords = A1VocabularyDataset.words.filter { it.dayNumber == dayNumber }
        return if (dayWords.isNotEmpty()) dayWords else A1VocabularyDataset.words.take(5)
    }

    suspend fun toggleFavorite(wordId: Int) {
        val current = wordProgressDao.getProgressForWord(wordId)
        if (current != null) {
            wordProgressDao.updateFavorite(wordId, !current.isFavorite)
        } else {
            val newProgress = WordProgressEntity(
                wordId = wordId,
                status = "NEW",
                isFavorite = true
            )
            wordProgressDao.insertOrUpdate(newProgress)
        }
    }

    suspend fun markWordKnowledge(wordId: Int, isKnown: Boolean) {
        val current = wordProgressDao.getProgressForWord(wordId)
        val now = System.currentTimeMillis()
        val oneDayMillis = 24 * 60 * 60 * 1000L

        val newStreak = if (isKnown) (current?.correctStreak ?: 0) + 1 else 0
        val newInterval = when {
            !isKnown -> 1
            newStreak >= 4 -> 14
            newStreak == 3 -> 7
            newStreak == 2 -> 3
            else -> 1
        }
        val newStatus = when {
            newStreak >= 4 -> "MASTERED"
            isKnown -> "LEARNING"
            else -> "DUE_FOR_REVIEW"
        }

        val updated = WordProgressEntity(
            wordId = wordId,
            status = newStatus,
            intervalDays = newInterval,
            nextReviewTimestamp = now + (newInterval * oneDayMillis),
            lastReviewTimestamp = now,
            reviewCount = (current?.reviewCount ?: 0) + 1,
            correctStreak = newStreak,
            isFavorite = current?.isFavorite ?: false,
            knownState = if (isKnown) "KNOWN" else "NEED_REVIEW"
        )
        wordProgressDao.insertOrUpdate(updated)

        // Record daily activity check
        checkAndUpdateStreak()
    }

    suspend fun recordQuizCompletion(dayNumber: Int, score: Int, total: Int) {
        val today = getTodayDate()
        val existing = dailyLessonDao.getLessonForDate(today)
        val wordsLearned = existing?.wordsLearnedCount ?: 5
        val completed = DailyLessonEntity(
            date = today,
            dayNumber = dayNumber,
            wordsLearnedCount = wordsLearned,
            isCompleted = true,
            quizScore = score,
            quizTotal = total,
            timestamp = System.currentTimeMillis()
        )
        dailyLessonDao.insertOrUpdate(completed)

        // Advance current day in settings if user achieved passing score
        val settings = getOrCreateSettings()
        val nextDay = if (dayNumber >= settings.currentDay) {
            (settings.currentDay % 21) + 1
        } else {
            settings.currentDay
        }
        userSettingsDao.saveSettings(settings.copy(currentDay = nextDay))

        checkAndUpdateStreak()
    }

    suspend fun checkAndUpdateStreak() {
        val settings = getOrCreateSettings()
        val today = getTodayDate()
        if (settings.lastActiveDate == today) return

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = dateFormat.format(calendar.time)

        val newStreak = if (settings.lastActiveDate == yesterday) {
            settings.currentStreak + 1
        } else if (settings.lastActiveDate.isEmpty()) {
            1
        } else {
            1
        }
        val best = maxOf(settings.bestStreak, newStreak)

        userSettingsDao.saveSettings(
            settings.copy(
                currentStreak = newStreak,
                bestStreak = best,
                lastActiveDate = today
            )
        )
    }

    suspend fun resetAllProgress() {
        wordProgressDao.clearAll()
        dailyLessonDao.clearAll()
        userSettingsDao.clearAll()
        userSettingsDao.saveSettings(UserSettingsEntity())
    }
}
