package de.jkrech.tutorial.chatty.ports.rest

import de.jkrech.tutorial.chatty.application.TranscriptionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
class TranscribeController(
    private val transcriptionService: TranscriptionService
) {

    final val logger: Logger = LoggerFactory.getLogger(TranscribeController::class.java)

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
        return Mono.fromCallable { transcriptionService.transcribe(audioFile) }
            .subscribeOn(Schedulers.boundedElastic())
    }
}