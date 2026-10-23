package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BottomNavBar
import com.example.ui.components.TopHeader
import com.example.ui.screens.DailyLessonScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.ReviewScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.EnglishOnTheGoTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.LearningViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnglishOnTheGoTheme {
                val viewModel: LearningViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                // Arabic RTL Layout support with English fallback
                val layoutDirection = if (uiState.settings.language == "ar") {
                    LayoutDirection.Rtl
                } else {
                    LayoutDirection.Ltr
                }

                // Handle back button for secondary screens
                BackHandler(enabled = uiState.currentScreen != AppScreen.HOME) {
                    viewModel.navigateTo(AppScreen.HOME)
                }

                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopHeader(
                                currentStreak = uiState.currentStreak,
                                onSettingsClick = {
                                    if (uiState.currentScreen == AppScreen.SETTINGS) {
                                        viewModel.navigateTo(AppScreen.HOME)
                                    } else {
                                        viewModel.navigateTo(AppScreen.SETTINGS)
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            BottomNavBar(
                                currentScreen = uiState.currentScreen,
                                onNavigate = { viewModel.navigateTo(it) }
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            AnimatedContent(
                                targetState = uiState.currentScreen,
                                transitionSpec = { fadeIn() togetherWith fadeOut() },
                                label = "screen_transition"
                            ) { screen ->
                                when (screen) {
                                    AppScreen.HOME -> HomeScreen(
                                        uiState = uiState,
                                        onNavigate = { viewModel.navigateTo(it) },
                                        onStartDailyLesson = { viewModel.navigateTo(AppScreen.DAILY_LESSON) },
                                        onStartReview = { viewModel.navigateTo(AppScreen.REVIEW) },
                                        onStartQuiz = { viewModel.startDailyQuiz() }
                                    )
                                    AppScreen.DAILY_LESSON -> DailyLessonScreen(
                                        uiState = uiState,
                                        onWordIndexChange = { viewModel.setDailyWordIndex(it) },
                                        onSpeak = { viewModel.speak(it) },
                                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                                        onMarkKnown = { id, known -> viewModel.markWordKnowledge(id, known) },
                                        onStartQuiz = { viewModel.startDailyQuiz() }
                                    )
                                    AppScreen.REVIEW -> ReviewScreen(
                                        uiState = uiState,
                                        onFlipCard = { viewModel.flipCard() },
                                        onScoreWord = { viewModel.scoreReviewWord(it) },
                                        onResetReviewSession = { viewModel.resetReviewSession() },
                                        onStartReviewQuiz = { viewModel.startReviewQuiz() },
                                        onSpeak = { viewModel.speak(it) }
                                    )
                                    AppScreen.QUIZ -> QuizScreen(
                                        uiState = uiState,
                                        onSelectOption = { viewModel.selectQuizOption(it) },
                                        onSubmitAnswer = { viewModel.submitQuizAnswer() },
                                        onNextQuestion = { viewModel.nextQuizQuestion() },
                                        onRetry = { viewModel.retryQuiz() },
                                        onSpeak = { viewModel.speak(it) },
                                        onNavigateHome = { viewModel.navigateTo(AppScreen.HOME) }
                                    )
                                    AppScreen.FAVORITES -> FavoritesScreen(
                                        uiState = uiState,
                                        onSearchChange = { viewModel.setSearchQuery(it) },
                                        onTopicFilterChange = { viewModel.setTopicFilter(it) },
                                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                                        onSpeak = { viewModel.speak(it) },
                                        onMarkKnown = { id, known -> viewModel.markWordKnowledge(id, known) }
                                    )
                                    AppScreen.PROGRESS -> ProgressScreen(
                                        uiState = uiState
                                    )
                                    AppScreen.SETTINGS -> SettingsScreen(
                                        uiState = uiState,
                                        onDailyGoalChange = { viewModel.updateDailyGoal(it) },
                                        onSpeechSettingsChange = { speed, pitch -> viewModel.updateSpeechSettings(speed, pitch) },
                                        onLanguageChange = { viewModel.updateLanguage(it) },
                                        onResetProgress = { viewModel.resetAllProgress() },
                                        onSpeakTest = { viewModel.speak(it) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
