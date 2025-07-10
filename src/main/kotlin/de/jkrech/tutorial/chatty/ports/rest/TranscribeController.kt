package de.jkrech.tutorial.chatty.ports.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions
import org.springframework.ai.openai.api.OpenAiAudioApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.io.File

@RestController
class TranscribeController {

    final val logger: Logger = LoggerFactory.getLogger(TranscribeController::class.java)

    @Autowired
    private var transcriptionModel: OpenAiAudioTranscriptionModel? = null

    @PostMapping("/ai/transcribe")
    fun transcribe(@RequestPart("audioFile") audioFile: FilePart): Mono<Map<String, String>> {
        return try {
            val tempFile = File.createTempFile("mic-audio", ".webm")
            audioFile.transferTo(tempFile)
            val fileResource = FileSystemResource(tempFile)

            transcribe(fileResource)
                .map { optionalResult ->
                    mapOf("transcription" to (optionalResult ?: "NO_TRANSCRIPTION"))
                }
                .doFinally { tempFile.delete() }
        } catch (exc: Exception) {
            logger.error("Error with transcription of audio file: ${exc.message}")
            Mono.error(exc)
        }
    }

    private fun transcribe(audioFile: Resource): Mono<String?> {
        val transcriptionOptions: OpenAiAudioTranscriptionOptions? = OpenAiAudioTranscriptionOptions.builder()
            .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
            .temperature(0f)
            .build()
        val transcriptionRequest = AudioTranscriptionPrompt(
            audioFile,
            transcriptionOptions
        )
        return Mono.fromCallable { transcriptionModel?.call(transcriptionRequest)?.result?.output }
            // boundedElastic scheduler lets run blocking calls in a separate thread pool
            .subscribeOn(Schedulers.boundedElastic())

    }
}