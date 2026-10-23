package com.example.data.model

enum class QuizType(val titleAr: String, val titleEn: String) {
    EN_TO_AR("ترجمة إلى العربية", "English to Arabic"),
    AR_TO_EN("ترجمة إلى الإنجليزية", "Arabic to English"),
    FILL_BLANK("إكمال الفراغ", "Fill in the Blank"),
    SENTENCE_MEANING("معنى الجملة", "Sentence Comprehension")
}

data class QuizQuestion(
    val id: Int,
    val type: QuizType,
    val questionPrompt: String,
    val highlightWord: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val relatedWord: VocabularyWord
)

data class QuizResult(
    val totalQuestions: Int,
    val correctAnswers: Int,
    val details: List<QuestionResultDetail>
)

data class QuestionResultDetail(
    val question: QuizQuestion,
    val selectedIndex: Int,
    val isCorrect: Boolean
)
