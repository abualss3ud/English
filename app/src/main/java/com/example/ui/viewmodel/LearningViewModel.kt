package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.dataset.A1VocabularyDataset
import com.example.data.generator.QuizGenerator
import com.example.data.local.AppDatabase
import com.example.data.local.DailyLessonEntity
import com.example.data.local.UserSettingsEntity
import com.example.data.local.WordProgressEntity
import com.example.data.model.QuestionResultDetail
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizResult
import com.example.data.model.VocabularyWord
import com.example.data.repository.LearningRepository
import com.example.service.TtsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    DAILY_LESSON,
    REVIEW,
    QUIZ,
    FAVORITES,
    PROGRESS,
    SETTINGS
}

data class LearningUiState(
    val currentScreen: AppScreen = AppScreen.HOME,
    val allWords: List<VocabularyWord> = A1VocabularyDataset.words,
    val progressMap: Map<Int, WordProgressEntity> = emptyMap(),
    val lessons: List<DailyLessonEntity> = emptyList(),
    val settings: UserSettingsEntity = UserSettingsEntity(),
    val todayDate: String = "",
    val wordsForToday: List<VocabularyWord> = emptyList(),
    val wordsDueForReview: List<VocabularyWord> = emptyList(),
    val favoriteWords: List<VocabularyWord> = emptyList(),
    val wordsLearnedTotal: Int = 0,
    val masteredWordsCount: Int = 0,
    val learningWordsCount: Int = 0,
    val todayWordsCompletedCount: Int = 0,
    val currentStreak: Int = 1,
    val bestStreak: Int = 1,
    // Daily lesson carousel
    val currentDailyWordIndex: Int = 0,
    // Interactive Quiz state
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val currentQuizIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isQuizAnswerSubmitted: Boolean = false,
    val quizAnswersList: List<QuestionResultDetail> = emptyList(),
    val quizResult: QuizResult? = null,
    val isQuizActive: Boolean = false,
    // SRS Review state
    val currentReviewIndex: Int = 0,
    val isReviewCardFlipped: Boolean = false,
    val isReviewCompleted: Boolean = false,
    // Search & Filter
    val searchQuery: String = "",
    val selectedTopicFilter: String? = null
)

class LearningViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val repository = LearningRepository(database)
    val ttsManager = TtsManager(application)

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    private val _dailyWordIndex = MutableStateFlow(0)
    private val _quizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    private val _currentQuizIndex = MutableStateFlow(0)
    private val _selectedOptionIndex = MutableStateFlow<Int?>(null)
    private val _isQuizAnswerSubmitted = MutableStateFlow(false)
    private val _quizAnswersList = MutableStateFlow<List<QuestionResultDetail>>(emptyList())
    private val _quizResult = MutableStateFlow<QuizResult?>(null)
    private val _isQuizActive = MutableStateFlow(false)

    private val _reviewIndex = MutableStateFlow(0)
    private val _isCardFlipped = MutableStateFlow(false)
    private val _isReviewCompleted = MutableStateFlow(false)

    private val _searchQuery = MutableStateFlow("")
    private val _topicFilter = MutableStateFlow<String?>(null)

    val uiState: StateFlow<LearningUiState> = combine(
        _currentScreen,
        repository.allProgressFlow,
        repository.allLessonsFlow,
        repository.settingsFlow
    ) { screen, progressList, lessons, settingsNullable ->
        val settings = settingsNullable ?: UserSettingsEntity()
        val today = repository.getTodayDate()
        val allWords = repository.getAllWords()

        val progressMap = progressList.associateBy { it.wordId }
        val now = System.currentTimeMillis()

        // Words due for spaced repetition
        val dueWords = allWords.filter { word ->
            val p = progressMap[word.id]
            p != null && (p.status == "DUE_FOR_REVIEW" || (p.nextReviewTimestamp in 1..now))
        }

        val favWords = allWords.filter { word ->
            progressMap[word.id]?.isFavorite == true
        }

        val todayWords = repository.getWordsForDay(settings.currentDay)

        // Today completed words
        val todayCompleted = todayWords.count { word ->
            val p = progressMap[word.id]
            p != null && (p.knownState == "KNOWN" || p.status == "LEARNING" || p.status == "MASTERED")
        }

        val masteredCount = progressList.count { it.status == "MASTERED" }
        val learningCount = progressList.count { it.status == "LEARNING" }
        val totalLearned = progressList.count { it.status != "NEW" && it.status.isNotEmpty() }

        LearningUiState(
            currentScreen = screen,
            allWords = allWords,
            progressMap = progressMap,
            lessons = lessons,
            settings = settings,
            todayDate = today,
            wordsForToday = todayWords,
            wordsDueForReview = dueWords,
            favoriteWords = favWords,
            wordsLearnedTotal = totalLearned,
            masteredWordsCount = masteredCount,
            learningWordsCount = learningCount,
            todayWordsCompletedCount = todayCompleted,
            currentStreak = settings.currentStreak,
            bestStreak = settings.bestStreak,
            currentDailyWordIndex = _dailyWordIndex.value,
            quizQuestions = _quizQuestions.value,
            currentQuizIndex = _currentQuizIndex.value,
            selectedOptionIndex = _selectedOptionIndex.value,
            isQuizAnswerSubmitted = _isQuizAnswerSubmitted.value,
            quizAnswersList = _quizAnswersList.value,
            quizResult = _quizResult.value,
            isQuizActive = _isQuizActive.value,
            currentReviewIndex = _reviewIndex.value,
            isReviewCardFlipped = _isCardFlipped.value,
            isReviewCompleted = _isReviewCompleted.value,
            searchQuery = _searchQuery.value,
            selectedTopicFilter = _topicFilter.value
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LearningUiState()
    )

    init {
        viewModelScope.launch {
            val settings = repository.getOrCreateSettings()
            ttsManager.setSpeedAndPitch(settings.speechSpeed, settings.speechPitch)
            repository.checkAndUpdateStreak()
        }
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun speak(text: String) {
        ttsManager.speak(text)
    }

    fun toggleFavorite(wordId: Int) {
        viewModelScope.launch {
            repository.toggleFavorite(wordId)
        }
    }

    fun markWordKnowledge(wordId: Int, isKnown: Boolean) {
        viewModelScope.launch {
            repository.markWordKnowledge(wordId, isKnown)
            val currentState = uiState.value
            if (currentState.currentDailyWordIndex < currentState.wordsForToday.size - 1) {
                _dailyWordIndex.value = currentState.currentDailyWordIndex + 1
            }
        }
    }

    fun setDailyWordIndex(index: Int) {
        _dailyWordIndex.value = index
    }

    // --- Quiz Logic ---
    fun startDailyQuiz() {
        val targetWords = uiState.value.wordsForToday
        val questions = QuizGenerator.generateQuiz(targetWords, uiState.value.allWords)
        _quizQuestions.value = questions
        _currentQuizIndex.value = 0
        _selectedOptionIndex.value = null
        _isQuizAnswerSubmitted.value = false
        _quizAnswersList.value = emptyList()
        _quizResult.value = null
        _isQuizActive.value = true
        _currentScreen.value = AppScreen.QUIZ
    }

    fun startReviewQuiz() {
        val due = uiState.value.wordsDueForReview
        val pool = if (due.isNotEmpty()) due else uiState.value.allWords.take(5)
        val questions = QuizGenerator.generateQuiz(pool, uiState.value.allWords)
        _quizQuestions.value = questions
        _currentQuizIndex.value = 0
        _selectedOptionIndex.value = null
        _isQuizAnswerSubmitted.value = false
        _quizAnswersList.value = emptyList()
        _quizResult.value = null
        _isQuizActive.value = true
        _currentScreen.value = AppScreen.QUIZ
    }

    fun selectQuizOption(index: Int) {
        if (!_isQuizAnswerSubmitted.value) {
            _selectedOptionIndex.value = index
        }
    }

    fun submitQuizAnswer() {
        val currentIdx = _currentQuizIndex.value
        val questions = _quizQuestions.value
        if (currentIdx in questions.indices && _selectedOptionIndex.value != null && !_isQuizAnswerSubmitted.value) {
            val q = questions[currentIdx]
            val isCorrect = (_selectedOptionIndex.value == q.correctIndex)
            _isQuizAnswerSubmitted.value = true

            val detail = QuestionResultDetail(
                question = q,
                selectedIndex = _selectedOptionIndex.value ?: -1,
                isCorrect = isCorrect
            )
            _quizAnswersList.value = _quizAnswersList.value + detail

            // Update SRS knowledge based on quiz answer
            viewModelScope.launch {
                repository.markWordKnowledge(q.relatedWord.id, isCorrect)
            }
        }
    }

    fun nextQuizQuestion() {
        val nextIdx = _currentQuizIndex.value + 1
        val questions = _quizQuestions.value
        if (nextIdx < questions.size) {
            _currentQuizIndex.value = nextIdx
            _selectedOptionIndex.value = null
            _isQuizAnswerSubmitted.value = false
        } else {
            // Quiz finished
            val answers = _quizAnswersList.value
            val correctCount = answers.count { it.isCorrect }
            val result = QuizResult(
                totalQuestions = questions.size,
                correctAnswers = correctCount,
                details = answers
            )
            _quizResult.value = result
            _isQuizActive.value = false

            // Save record in repository
            viewModelScope.launch {
                repository.recordQuizCompletion(
                    dayNumber = uiState.value.settings.currentDay,
                    score = correctCount,
                    total = questions.size
                )
            }
        }
    }

    fun retryQuiz() {
        val currentQuestions = _quizQuestions.value
        if (currentQuestions.isNotEmpty()) {
            _currentQuizIndex.value = 0
            _selectedOptionIndex.value = null
            _isQuizAnswerSubmitted.value = false
            _quizAnswersList.value = emptyList()
            _quizResult.value = null
            _isQuizActive.value = true
        } else {
            startDailyQuiz()
        }
    }

    // --- Review (SRS Flashcards) Logic ---
    fun flipCard() {
        _isCardFlipped.value = !_isCardFlipped.value
    }

    fun scoreReviewWord(isKnown: Boolean) {
        val dueWords = uiState.value.wordsDueForReview
        val pool = if (dueWords.isNotEmpty()) dueWords else uiState.value.wordsForToday
        val currentIndex = _reviewIndex.value

        if (currentIndex in pool.indices) {
            val word = pool[currentIndex]
            viewModelScope.launch {
                repository.markWordKnowledge(word.id, isKnown)
            }
        }

        if (currentIndex < pool.size - 1) {
            _reviewIndex.value = currentIndex + 1
            _isCardFlipped.value = false
        } else {
            _isReviewCompleted.value = true
        }
    }

    fun resetReviewSession() {
        _reviewIndex.value = 0
        _isCardFlipped.value = false
        _isReviewCompleted.value = false
    }

    // --- Search & Filter ---
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setTopicFilter(topic: String?) {
        _topicFilter.value = topic
    }

    // --- Settings ---
    fun updateDailyGoal(goal: Int) {
        viewModelScope.launch {
            val current = uiState.value.settings
            repository.updateSettings(current.copy(dailyGoal = goal))
        }
    }

    fun updateSpeechSettings(speed: Float, pitch: Float) {
        viewModelScope.launch {
            val current = uiState.value.settings
            repository.updateSettings(current.copy(speechSpeed = speed, speechPitch = pitch))
            ttsManager.setSpeedAndPitch(speed, pitch)
        }
    }

    fun updateLanguage(lang: String) {
        viewModelScope.launch {
            val current = uiState.value.settings
            repository.updateSettings(current.copy(language = lang))
        }
    }

    fun resetAllProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
            _dailyWordIndex.value = 0
            _currentQuizIndex.value = 0
            _quizResult.value = null
            _isQuizActive.value = false
            _reviewIndex.value = 0
            _isReviewCompleted.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}
