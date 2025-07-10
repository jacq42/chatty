package de.jkrech.tutorial.chatty.application

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions
import org.springframework.ai.openai.api.OpenAiAudioApi
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class TranscriptionService(
    private val transcriptionModel: OpenAiAudioTranscriptionModel
) {
    private val useFakeData = true

    fun transcribe(audioFile: Resource): String {
        if (useFakeData) {
            return "Tell me a joke"
        }
        val transcriptionOptions: OpenAiAudioTranscriptionOptions? = OpenAiAudioTranscriptionOptions.builder()
            .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
            .temperature(0f)
            .build()
        val transcriptionRequest = AudioTranscriptionPrompt(
            audioFile,
            transcriptionOptions
        )
        return transcriptionModel.call(transcriptionRequest).result.output
    }
}