package de.jkrech.tutorial.chatty.application

import org.springframework.ai.openai.OpenAiAudioSpeechModel
import org.springframework.ai.openai.OpenAiAudioSpeechOptions
import org.springframework.ai.openai.api.OpenAiAudioApi
import org.springframework.ai.openai.audio.speech.SpeechPrompt
import org.springframework.ai.openai.audio.speech.SpeechResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class SpeechService(
    private val openAiAudioSpeechModel: OpenAiAudioSpeechModel
) {

    private val useFakeData = true

    @Value("classpath:/speech/tell-me-a-joke.flac")
    private val fakeAudioFile: Resource? = null

    fun speech(message: String): ByteArray {
        if (useFakeData) {
            return fileToByteArray(fakeAudioFile)
        }
        val speechPrompt = SpeechPrompt(message, speechOptions)
        return openAiAudioSpeechModel.call(speechPrompt).result.output
    }

    fun speechStream(message: String): Flux<SpeechResponse?>? {
        val speechPrompt = SpeechPrompt(message, speechOptions)
        return openAiAudioSpeechModel.stream(speechPrompt)
    }

    private fun fileToByteArray(audioResource: Resource?): ByteArray {
        return audioResource?.inputStream?.readAllBytes() ?: ByteArray(0)
    }

    companion object {
        val speechOptions: OpenAiAudioSpeechOptions = OpenAiAudioSpeechOptions.builder()
            .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
            .speed(1.0f)
            .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
            .model(OpenAiAudioApi.TtsModel.TTS_1.value)
            .build()
    }
}