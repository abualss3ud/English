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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.ui.components.FlipCard
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldSoft
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.viewmodel.LearningUiState

@Composable
fun ReviewScreen(
    uiState: LearningUiState,
    onFlipCard: () -> Unit,
    onScoreWord: (Boolean) -> Unit,
    onResetReviewSession: () -> Unit,
    onStartReviewQuiz: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val dueWords = uiState.wordsDueForReview
    val pool = if (dueWords.isNotEmpty()) dueWords else uiState.wordsForToday
    val currentIndex = uiState.currentReviewIndex.coerceIn(0, (pool.size - 1).coerceAtLeast(0))
    val currentWord = if (pool.isNotEmpty()) pool[currentIndex] else null

    val scrollState = rememberScrollState()

    // Completed Review Session View
    if (uiState.isReviewCompleted) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp)
                .testTag("review_completed_screen"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(EmeraldSoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = EmeraldSuccess,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "اكتملت جلسة المراجعة! 🎉",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "تم تحديث جدول التكرار المتباعد للكلمات. ستظهر الكلمات التي أتقنتها في فترات متباعدة تدريجياً لضمان ثباتها.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onResetReviewSession,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("restart_review_btn"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NavyPrimary,
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("مراجعة جولة أخرى", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onStartReviewQuiz,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("start_review_quiz_btn"),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlue)
            ) {
                Icon(imageVector = Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(18.dp), tint = ElectricBlue)
                Spacer(modifier = Modifier.width(8.dp))
                Text("بدء اختبار على كلمات المراجعة", fontWeight = FontWeight.Bold, color = ElectricBlue)
            }
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("review_screen")
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "المراجعة الذكية (SRS)",
                    style = MaterialTheme.typography.titleLarge,
                    color = NavyPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (dueWords.isNotEmpty())
                        "لديك ${dueWords.size} كلمات مستحقة للمراجعة اليوم"
                    else
                        "مراجعة إضافية لتثبيت الكلمات",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = ElectricBlue.copy(alpha = 0.12f)
            ) {
                Text(
                    text = "بطاقة ${currentIndex + 1} من ${pool.size}",
                    fontSize = 13.sp,
                    color = ElectricBlue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SRS Flip Card
        if (currentWord != null) {
            FlipCard(
                word = currentWord,
                isFlipped = uiState.isReviewCardFlipped,
                onFlip = onFlipCard,
                onSpeak = onSpeak
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Spaced Repetition Feedback Buttons
        Text(
            text = "كيف تتذكر هذه الكلمة؟",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { onScoreWord(false) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .testTag("srs_again_btn"),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, AmberWarning)
            ) {
                Icon(imageVector = Icons.Default.Replay, contentDescription = null, tint = AmberWarning, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("أحتاج مراجعة", fontWeight = FontWeight.Bold, color = AmberWarning, fontSize = 13.sp)
                    Text("إعادة قريبة", fontSize = 10.sp, color = AmberWarning)
                }
            }

            Button(
                onClick = { onScoreWord(true) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .testTag("srs_good_btn"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmeraldSuccess,
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("عرفتها بسهولة", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("تباعد الفترات", fontSize = 10.sp, color = Color.White.copy(alpha = 0.9f))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SRS System Breakdown Legend
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = ElectricBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "كيف يعمل نظام التكرار المتباعد؟",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "• الكلمات الجديدة تراجع بعد يوم واحد.\n• الكلمات الصحيحة تتضاعف فترات مراجعتها (1 -> 3 -> 7 -> 14 يوماً).\n• عند إتقان الكلمة 4 مرات متتالية تصبح 'متقنة' في رصيدك.",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 17.sp
                )
            }
        }
    }
}
