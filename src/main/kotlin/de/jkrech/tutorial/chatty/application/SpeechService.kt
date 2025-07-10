package de.jkrech.tutorial.chatty.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.openai.OpenAiAudioSpeechOptions
import org.springframework.ai.openai.api.OpenAiAudioApi
import org.springframework.ai.openai.audio.speech.SpeechModel
import org.springframework.ai.openai.audio.speech.SpeechPrompt
import org.springframework.stereotype.Service

@Service
class SpeechService(
    private val speechModel: SpeechModel
) {
    private final val logger: Logger = LoggerFactory.getLogger(SpeechService::class.java)

    fun speech(message: String): ByteArray {
        val speechPrompt = SpeechPrompt(message, speechOptions)
        return speechModel.call(speechPrompt).result.output
    }

//    fun speechStream(message: String): Flux<SpeechResponse?>? {
//        val speechPrompt = SpeechPrompt(message, speechOptions)
//        return speechModel.stream(speechPrompt)
//    }

    companion object {
        val speechOptions: OpenAiAudioSpeechOptions = OpenAiAudioSpeechOptions.builder()
            .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
            .speed(1.0f)
            .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
            .model(OpenAiAudioApi.TtsModel.TTS_1.value)
            .build()
    }
}