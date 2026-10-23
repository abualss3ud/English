package com.example.data.model

data class VocabularyWord(
    val id: Int,
    val word: String,
    val phonetic: String,
    val arabicMeaning: String,
    val partOfSpeech: String,
    val partOfSpeechArabic: String,
    val englishExample: String,
    val arabicExample: String,
    val topic: String,
    val topicArabic: String,
    val dayNumber: Int
)

enum class SrsStatus(val labelAr: String, val labelEn: String) {
    NEW("جديدة", "New"),
    LEARNING("قيد التعلم", "Learning"),
    DUE_FOR_REVIEW("بحاجة لمراجعة", "Due for Review"),
    MASTERED("متقنة", "Mastered")
}
