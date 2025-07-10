package de.jkrech.tutorial.chatty.ports.rest

import de.jkrech.tutorial.chatty.application.TranscriptionService
import de.jkrech.tutorial.chatty.domain.AudioResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
class TranscribeController(
    private val transcriptionService: TranscriptionService
) {

    final val logger: Logger = LoggerFactory.getLogger(TranscribeController::class.java)

    @PostMapping("/ai/transcribe")
    fun transcribe(@RequestPart("audioFile") audioFile: FilePart): Mono<Map<String, String>> {
        return AudioResource.from(audioFile)
            .flatMap { audioResource ->
                transcribe(audioResource.value)
                    .map { optionalResult ->
                        mapOf("transcription" to (optionalResult ?: "NO_TRANSCRIPTION"))
                    }
            }
    }

    private fun transcribe(audioFile: Resource): Mono<String?> {
        return Mono.fromCallable { transcriptionService.transcribe(audioFile) }
            .subscribeOn(Schedulers.boundedElastic())
    }
}