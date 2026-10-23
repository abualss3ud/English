package com.example.service

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TtsManager(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    var isInitialized = false
        private set

    private var speechRate = 1.0f
    private var speechPitch = 1.0f

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w("TtsManager", "US English not directly supported on this engine.")
            } else {
                isInitialized = true
                tts?.setSpeechRate(speechRate)
                tts?.setPitch(speechPitch)
            }
        } else {
            Log.e("TtsManager", "Initialization of TextToSpeech failed.")
        }
    }

    fun setSpeedAndPitch(rate: Float, pitch: Float) {
        this.speechRate = rate
        this.speechPitch = pitch
        if (isInitialized) {
            tts?.setSpeechRate(rate)
            tts?.setPitch(pitch)
        }
    }

    fun speak(text: String) {
        if (isInitialized) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "vocab_utterance")
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isInitialized = false
    }
}
