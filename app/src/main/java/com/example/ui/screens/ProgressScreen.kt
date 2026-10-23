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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.dataset.A1VocabularyDataset
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.viewmodel.LearningUiState

@Composable
fun ProgressScreen(
    uiState: LearningUiState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val totalWords = uiState.allWords.size
    val studiedWords = uiState.wordsLearnedTotal
    val masteredWords = uiState.masteredWordsCount
    val learningWords = uiState.learningWordsCount
    val completedLessons = uiState.lessons.count { it.isCompleted }

    // Real quiz accuracy calculation from local records
    val totalQuizQuestions = uiState.lessons.sumOf { it.quizTotal }
    val totalQuizScore = uiState.lessons.sumOf { it.quizScore }
    val quizAccuracy = if (totalQuizQuestions > 0) (totalQuizScore * 100) / totalQuizQuestions else 100

    val overallProgressFraction = (studiedWords.toFloat() / totalWords.toFloat()).coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("progress_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "التقدم والإحصائيات",
            style = MaterialTheme.typography.titleLarge,
            color = NavyPrimary,
            fontWeight = FontWeight.Bold
        )

        // Overall A1 Completion Level Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = NavyPrimary)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "المستوى A1 (مبتدئ)",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$studiedWords من أصل $totalWords كلمة أساسية",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 13.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${(overallProgressFraction * 100).toInt()}%",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LinearProgressIndicator(
                    progress = { overallProgressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    color = EmeraldSuccess,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
            }
        }

        // Stats Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProgressStatItem(
                title = "كلمات قيد التعلم",
                value = "$learningWords",
                icon = Icons.Default.School,
                color = ElectricBlue,
                modifier = Modifier.weight(1f)
            )
            ProgressStatItem(
                title = "كلمات متقنة",
                value = "$masteredWords",
                icon = Icons.Default.CheckCircle,
                color = EmeraldSuccess,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProgressStatItem(
                title = "الدروس المكتملة",
                value = "$completedLessons",
                icon = Icons.Default.MenuBook,
                color = NavyPrimary,
                modifier = Modifier.weight(1f)
            )
            ProgressStatItem(
                title = "دقة الاختبارات",
                value = "$quizAccuracy%",
                icon = Icons.Default.Quiz,
                color = AmberWarning,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProgressStatItem(
                title = "سلسلة الحماس الحالية",
                value = "${uiState.currentStreak} يوم",
                icon = Icons.Default.LocalFireDepartment,
                color = Color(0xFFF97316),
                modifier = Modifier.weight(1f)
            )
            ProgressStatItem(
                title = "أفضل رقم قياسي",
                value = "${uiState.bestStreak} يوم",
                icon = Icons.Default.EmojiEvents,
                color = AmberWarning,
                modifier = Modifier.weight(1f)
            )
        }

        // Topic Mastery Breakdown
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "إتقان المفردات حسب الموضوعات",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "توزيع الكلمات المتقنة عبر مواقف الحياة اليومية",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                val topics = A1VocabularyDataset.getTopics()
                topics.forEach { topic ->
                    val topicWords = uiState.allWords.filter { it.topicArabic == topic }
                    val masteredInTopic = topicWords.count {
                        uiState.progressMap[it.id]?.status == "MASTERED" ||
                                uiState.progressMap[it.id]?.status == "LEARNING"
                    }
                    val fraction = if (topicWords.isNotEmpty()) masteredInTopic.toFloat() / topicWords.size.toFloat() else 0f

                    Column(modifier = Modifier.padding(vertical = 6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = topic,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = NavyPrimary
                            )
                            Text(
                                text = "$masteredInTopic / ${topicWords.size}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { fraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = ElectricBlue,
                            trackColor = SlateBorder
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProgressStatItem(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = NavyPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
