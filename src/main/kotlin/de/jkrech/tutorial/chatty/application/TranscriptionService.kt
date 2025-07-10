package de.jkrech.tutorial.chatty.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse
import org.springframework.ai.model.Model
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions
import org.springframework.ai.openai.api.OpenAiAudioApi
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class TranscriptionService(
    private val transcriptionModel: Model<AudioTranscriptionPrompt, AudioTranscriptionResponse>
) {
    private final val logger: Logger = LoggerFactory.getLogger(TranscriptionService::class.java)

    fun transcribe(audioFile: Resource): String {
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