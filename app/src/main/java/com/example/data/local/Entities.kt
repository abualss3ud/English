package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_progress")
data class WordProgressEntity(
    @PrimaryKey val wordId: Int,
    val status: String = "NEW", // NEW, LEARNING, DUE, MASTERED
    val intervalDays: Int = 1,
    val nextReviewTimestamp: Long = 0L,
    val lastReviewTimestamp: Long = 0L,
    val reviewCount: Int = 0,
    val correctStreak: Int = 0,
    val isFavorite: Boolean = false,
    val knownState: String = "UNSEEN" // "KNOWN", "NEED_REVIEW", "UNSEEN"
)

@Entity(tableName = "daily_lessons")
data class DailyLessonEntity(
    @PrimaryKey val date: String, // YYYY-MM-DD
    val dayNumber: Int,
    val wordsLearnedCount: Int,
    val isCompleted: Boolean,
    val quizScore: Int,
    val quizTotal: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val dailyGoal: Int = 5,
    val currentDay: Int = 1,
    val speechSpeed: Float = 1.0f,
    val speechPitch: Float = 1.0f,
    val language: String = "ar", // "ar" or "en"
    val currentStreak: Int = 1,
    val bestStreak: Int = 1,
    val lastActiveDate: String = ""
)
