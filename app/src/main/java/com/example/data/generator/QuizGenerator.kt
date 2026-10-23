package com.example.data.generator

import com.example.data.dataset.A1VocabularyDataset
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizType
import com.example.data.model.VocabularyWord

object QuizGenerator {
    fun generateQuiz(words: List<VocabularyWord>, allWords: List<VocabularyWord> = A1VocabularyDataset.words): List<QuizQuestion> {
        val targetWords = if (words.size >= 5) words.take(5) else words + allWords.filter { it !in words }.take(5 - words.size)
        val questions = mutableListOf<QuizQuestion>()

        targetWords.forEachIndexed { index, word ->
            // Distribute different question types among the 5 questions
            val type = when (index % 4) {
                0 -> QuizType.EN_TO_AR
                1 -> QuizType.AR_TO_EN
                2 -> QuizType.FILL_BLANK
                else -> QuizType.SENTENCE_MEANING
            }

            val question = createQuestionForWord(index + 1, word, type, allWords)
            questions.add(question)
        }

        return questions
    }

    private fun createQuestionForWord(
        id: Int,
        word: VocabularyWord,
        type: QuizType,
        allWords: List<VocabularyWord>
    ): QuizQuestion {
        val otherWords = allWords.filter { it.id != word.id }.shuffled()

        return when (type) {
            QuizType.EN_TO_AR -> {
                val distractors = otherWords.take(3).map { it.arabicMeaning }
                val options = (distractors + word.arabicMeaning).shuffled()
                val correctIdx = options.indexOf(word.arabicMeaning)
                QuizQuestion(
                    id = id,
                    type = type,
                    questionPrompt = "ما المعنى العربي لكلمة:",
                    highlightWord = word.word,
                    options = options,
                    correctIndex = correctIdx,
                    explanation = "المعنى الصحيح هو '${word.arabicMeaning}'. مثال: ${word.englishExample}",
                    relatedWord = word
                )
            }
            QuizType.AR_TO_EN -> {
                val distractors = otherWords.take(3).map { it.word }
                val options = (distractors + word.word).shuffled()
                val correctIdx = options.indexOf(word.word)
                QuizQuestion(
                    id = id,
                    type = type,
                    questionPrompt = "ما الكلمة الإنجليزية التي تعني:",
                    highlightWord = word.arabicMeaning,
                    options = options,
                    correctIndex = correctIdx,
                    explanation = "الكلمة بالإنجليزية هي '${word.word}'. النطق: ${word.phonetic}",
                    relatedWord = word
                )
            }
            QuizType.FILL_BLANK -> {
                // Replace the word in the example sentence with blank: ______
                val regex = Regex("\\b" + Regex.escape(word.word) + "\\b", RegexOption.IGNORE_CASE)
                val blankSentence = if (regex.containsMatchIn(word.englishExample)) {
                    word.englishExample.replace(regex, "______")
                } else {
                    "${word.englishExample.substringBefore(" ")} ______ ${word.englishExample.substringAfter(" ", "")}"
                }
                val distractors = otherWords.filter { it.partOfSpeech == word.partOfSpeech || true }.take(3).map { it.word }
                val options = (distractors + word.word).shuffled()
                val correctIdx = options.indexOf(word.word)
                QuizQuestion(
                    id = id,
                    type = type,
                    questionPrompt = "اختر الكلمة المناسبة لإكمال الجملة:\n$blankSentence",
                    highlightWord = word.arabicExample,
                    options = options,
                    correctIndex = correctIdx,
                    explanation = "الجملة الكاملة: '${word.englishExample}' ومعناها: '${word.arabicExample}'",
                    relatedWord = word
                )
            }
            QuizType.SENTENCE_MEANING -> {
                val distractors = otherWords.take(3).map { it.arabicExample }
                val options = (distractors + word.arabicExample).shuffled()
                val correctIdx = options.indexOf(word.arabicExample)
                QuizQuestion(
                    id = id,
                    type = type,
                    questionPrompt = "ما هو المعنى الصحيح لهذه الجملة؟",
                    highlightWord = word.englishExample,
                    options = options,
                    correctIndex = correctIdx,
                    explanation = "المعنى الصحيح للجملة هو: '${word.arabicExample}'",
                    relatedWord = word
                )
            }
        }
    }
}
