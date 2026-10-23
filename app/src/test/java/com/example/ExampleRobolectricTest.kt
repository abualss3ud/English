package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.dataset.A1VocabularyDataset
import com.example.data.generator.QuizGenerator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("English On The Go", appName)
    }

    @Test
    fun `vocabulary dataset has at least 100 words`() {
        val words = A1VocabularyDataset.words
        assertTrue("Dataset should contain at least 100 words, found: ${words.size}", words.size >= 100)
        words.forEach { word ->
            assertTrue(word.word.isNotEmpty())
            assertTrue(word.arabicMeaning.isNotEmpty())
            assertTrue(word.phonetic.isNotEmpty())
            assertTrue(word.englishExample.isNotEmpty())
            assertTrue(word.arabicExample.isNotEmpty())
        }
    }

    @Test
    fun `quiz generator generates 5 questions with options and explanations`() {
        val sampleWords = A1VocabularyDataset.words.take(5)
        val quiz = QuizGenerator.generateQuiz(sampleWords)
        assertEquals(5, quiz.size)
        quiz.forEach { q ->
            assertEquals(4, q.options.size)
            assertTrue(q.correctIndex in 0..3)
            assertNotNull(q.explanation)
            assertTrue(q.explanation.isNotEmpty())
        }
    }
}
