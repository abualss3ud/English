package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldSoft
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.RoseError
import com.example.ui.theme.RoseSoft
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateSurfaceVariant
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.LearningUiState

@Composable
fun QuizScreen(
    uiState: LearningUiState,
    onSelectOption: (Int) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onRetry: () -> Unit,
    onSpeak: (String) -> Unit,
    onNavigateHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // Results Screen when Quiz is completed
    if (uiState.quizResult != null) {
        val result = uiState.quizResult
        val score = result.correctAnswers
        val total = result.totalQuestions
        val percentage = if (total > 0) (score * 100) / total else 0
        val isPassed = percentage >= 60

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp)
                .testTag("quiz_results_screen"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Score Banner
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(if (isPassed) EmeraldSoft else RoseSoft),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$score / $total",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isPassed) EmeraldSuccess else RoseError
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (isPassed) "رائع جداً! أحسنت 👏" else "محاولة جيدة، يمكنك التحسن!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (isPassed)
                    "لقد اجتزت اختبار اليوم بنجاح وتم تسجيل تقدمك وحفظ الكلمات!"
                else
                    "راجع الكلمات وأعد الاختبار لتثبيت المفردات في ذاكرتك طويلة المدى.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Question Breakdown List
            Text(
                text = "مراجعة إجاباتك بالتفصيل:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            result.details.forEachIndexed { index, detail ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (detail.isCorrect) EmeraldSuccess.copy(alpha = 0.5f) else RoseError.copy(alpha = 0.5f)
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "سؤال ${index + 1}: ${detail.question.type.titleAr}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ElectricBlue
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (detail.isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                                    contentDescription = null,
                                    tint = if (detail.isCorrect) EmeraldSuccess else RoseError,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (detail.isCorrect) "صحيحة" else "غير صحيحة",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (detail.isCorrect) EmeraldSuccess else RoseError
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${detail.question.questionPrompt} ${detail.question.highlightWord}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = NavyPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        val correctOption = detail.question.options[detail.question.correctIndex]
                        Text(
                            text = "الإجابة الصحيحة: $correctOption",
                            fontSize = 13.sp,
                            color = EmeraldSuccess,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "الشرح: ${detail.question.explanation}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons: Retry and Home
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onRetry,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("retry_quiz_btn"),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, NavyPrimary)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("إعادة الاختبار", fontWeight = FontWeight.Bold, color = NavyPrimary)
                }

                Button(
                    onClick = onNavigateHome,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("home_after_quiz_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NavyPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("الرئيسية", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
        return
    }

    // Active Quiz Question View
    val questions = uiState.quizQuestions
    val currentIdx = uiState.currentQuizIndex
    val currentQuestion = if (currentIdx in questions.indices) questions[currentIdx] else null

    if (currentQuestion == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("لا يوجد اختبار نشط حالياً", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("بدء اختبار جديد")
            }
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("active_quiz_screen")
    ) {
        // Progress header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = ElectricBlue.copy(alpha = 0.12f)
            ) {
                Text(
                    text = currentQuestion.type.titleAr,
                    fontSize = 12.sp,
                    color = ElectricBlue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Text(
                text = "السؤال ${currentIdx + 1} من ${questions.size}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        val progress = (currentIdx + 1).toFloat() / questions.size.toFloat()
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = ElectricBlue,
            trackColor = SlateBorder
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Question Prompt Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentQuestion.questionPrompt,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentQuestion.highlightWord,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = NavyPrimary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { onSpeak(currentQuestion.relatedWord.word) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "استمع للنطق",
                            tint = ElectricBlue
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Options List (4 interactive buttons)
        currentQuestion.options.forEachIndexed { optionIndex, optionText ->
            val isSelected = uiState.selectedOptionIndex == optionIndex
            val isSubmitted = uiState.isQuizAnswerSubmitted
            val isCorrectOption = optionIndex == currentQuestion.correctIndex

            val backgroundColor = when {
                !isSubmitted && isSelected -> ElectricBlue.copy(alpha = 0.15f)
                isSubmitted && isCorrectOption -> EmeraldSoft
                isSubmitted && isSelected && !isCorrectOption -> RoseSoft
                else -> Color.White
            }

            val borderColor = when {
                !isSubmitted && isSelected -> ElectricBlue
                isSubmitted && isCorrectOption -> EmeraldSuccess
                isSubmitted && isSelected && !isCorrectOption -> RoseError
                else -> SlateBorder
            }

            val textColor = when {
                !isSubmitted && isSelected -> ElectricBlue
                isSubmitted && isCorrectOption -> Color(0xFF14532D)
                isSubmitted && isSelected && !isCorrectOption -> Color(0xFF7F1D1D)
                else -> NavyPrimary
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.5.dp, borderColor, RoundedCornerShape(16.dp))
                    .clickable(enabled = !isSubmitted) { onSelectOption(optionIndex) }
                    .testTag("quiz_option_$optionIndex"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = optionText,
                        fontSize = 16.sp,
                        fontWeight = if (isSelected || (isSubmitted && isCorrectOption)) FontWeight.Bold else FontWeight.Medium,
                        color = textColor
                    )

                    if (isSubmitted && isCorrectOption) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = EmeraldSuccess,
                            modifier = Modifier.size(20.dp)
                        )
                    } else if (isSubmitted && isSelected && !isCorrectOption) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = RoseError,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Immediate Feedback & Explanation Card
        if (uiState.isQuizAnswerSubmitted) {
            val isAnswerCorrect = uiState.selectedOptionIndex == currentQuestion.correctIndex

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isAnswerCorrect) EmeraldSoft else RoseSoft
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isAnswerCorrect) EmeraldSuccess else RoseError
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isAnswerCorrect) Icons.Default.CheckCircle else Icons.Default.Info,
                            contentDescription = null,
                            tint = if (isAnswerCorrect) EmeraldSuccess else RoseError,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isAnswerCorrect) "إجابة صحيحة! أحسنت 👏" else "إجابة غير صحيحة!",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isAnswerCorrect) Color(0xFF14532D) else Color(0xFF7F1D1D)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = currentQuestion.explanation,
                        fontSize = 13.sp,
                        color = if (isAnswerCorrect) Color(0xFF166534) else Color(0xFF991B1B),
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Next Question or Finish Button
            Button(
                onClick = onNextQuestion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("next_quiz_question_btn"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NavyPrimary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (currentIdx < questions.size - 1) "السؤال التالي" else "عرض نتائج الاختبار",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        } else {
            // Confirm/Submit Answer Button
            Button(
                onClick = onSubmitAnswer,
                enabled = uiState.selectedOptionIndex != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("submit_quiz_answer_btn"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ElectricBlue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "تأكيد الإجابة",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}
