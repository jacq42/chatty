package de.jkrech.tutorial.chatty.ports.rest

import de.jkrech.tutorial.chatty.application.SpeechService
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
class SpeechController(
    private val speechService: SpeechService
) {

    @GetMapping("/ai/speech")
    fun generateSpeech(@RequestParam(value = "message", defaultValue = "Tell me a joke") message: String): Mono<ResponseEntity<ByteArrayResource>> {
        return speech(message)
            .map { toResponse(it) }
    }

    private fun speech(message: String): Mono<ByteArray?> {
        return Mono.fromCallable { speechService.speech(message) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    private fun toResponse(bytes: ByteArray?): ResponseEntity<ByteArrayResource> {
        if (bytes == null) {
            return ResponseEntity.notFound().build()
        } else {
            val resource = ByteArrayResource(bytes)
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=speech.mp3")
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .contentLength(bytes.size.toLong())
                .body(resource)
        }
    }
}