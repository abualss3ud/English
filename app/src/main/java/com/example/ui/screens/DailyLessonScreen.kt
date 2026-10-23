package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.WordCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.viewmodel.LearningUiState

@Composable
fun DailyLessonScreen(
    uiState: LearningUiState,
    onWordIndexChange: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onMarkKnown: (Int, Boolean) -> Unit,
    onStartQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    val words = uiState.wordsForToday
    val currentIndex = uiState.currentDailyWordIndex.coerceIn(0, (words.size - 1).coerceAtLeast(0))
    val currentWord = if (words.isNotEmpty()) words[currentIndex] else null
    val isFavorite = if (currentWord != null) uiState.progressMap[currentWord.id]?.isFavorite == true else false

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("daily_lesson_screen")
    ) {
        // Header with Day number and Topic
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "درس اليوم ${uiState.settings.currentDay}",
                    style = MaterialTheme.typography.titleLarge,
                    color = NavyPrimary,
                    fontWeight = FontWeight.Bold
                )
                if (currentWord != null) {
                    Text(
                        text = "الموضوع: ${currentWord.topicArabic}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Word step counter pill
            Surface(
                color = ElectricBlue.copy(alpha = 0.12f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "الكلمة ${currentIndex + 1} من ${words.size}",
                    color = ElectricBlue,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Progress bar for the 5 words
        val progress = if (words.isNotEmpty()) (currentIndex + 1).toFloat() / words.size.toFloat() else 0f
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = ElectricBlue,
            trackColor = SlateBorder
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Word Card
        if (currentWord != null) {
            WordCard(
                word = currentWord,
                isFavorite = isFavorite,
                onToggleFavorite = { onToggleFavorite(currentWord.id) },
                onSpeak = onSpeak,
                onKnownClick = { onMarkKnown(currentWord.id, true) },
                onNeedReviewClick = { onMarkKnown(currentWord.id, false) },
                showActionButtons = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Carousel Navigation: Previous and Next Word buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedIconButton(
                onClick = {
                    if (currentIndex > 0) onWordIndexChange(currentIndex - 1)
                },
                enabled = currentIndex > 0,
                modifier = Modifier
                    .size(48.dp)
                    .testTag("prev_word_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "الكلمة السابقة",
                    tint = if (currentIndex > 0) NavyPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            }

            // Dots indicator for 5 words
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                words.indices.forEach { idx ->
                    val isCurrent = idx == currentIndex
                    val isLearned = uiState.progressMap[words[idx].id]?.knownState == "KNOWN"
                    Box(
                        modifier = Modifier
                            .size(if (isCurrent) 12.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isCurrent -> ElectricBlue
                                    isLearned -> EmeraldSuccess
                                    else -> SlateBorder
                                }
                            )
                    )
                }
            }

            OutlinedIconButton(
                onClick = {
                    if (currentIndex < words.size - 1) onWordIndexChange(currentIndex + 1)
                },
                enabled = currentIndex < words.size - 1,
                modifier = Modifier
                    .size(48.dp)
                    .testTag("next_word_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "الكلمة التالية",
                    tint = if (currentIndex < words.size - 1) NavyPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Prompt to take Quiz if user reaches the end or has studied words
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(EmeraldSuccess.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Celebration,
                        contentDescription = null,
                        tint = EmeraldSuccess,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "أتقنت كلمات اليوم؟ اختبر معلوماتك الآن!",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "اختبار تفاعلي من 5 أسئلة مع تغذية راجعة فورية لتأكيد حفظ الكلمات",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onStartQuiz,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("launch_quiz_from_lesson_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NavyPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "بدء اختبار درس اليوم (5 أسئلة)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
