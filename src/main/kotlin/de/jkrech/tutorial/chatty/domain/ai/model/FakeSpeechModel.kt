package de.jkrech.tutorial.chatty.domain.ai.model

import org.springframework.ai.openai.audio.speech.Speech
import org.springframework.ai.openai.audio.speech.SpeechModel
import org.springframework.ai.openai.audio.speech.SpeechPrompt
import org.springframework.ai.openai.audio.speech.SpeechResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource

class FakeSpeechModel: SpeechModel {

    @Value("classpath:/speech/tell-me-a-joke.flac")
    private val fakeAudioFile: Resource? = null

    override fun call(request: SpeechPrompt): SpeechResponse {
        return SpeechResponse(Speech(fileToByteArray(fakeAudioFile)));
    }

    private fun fileToByteArray(audioResource: Resource?): ByteArray {
        return audioResource?.inputStream?.readAllBytes() ?: ByteArray(0)
    }
}