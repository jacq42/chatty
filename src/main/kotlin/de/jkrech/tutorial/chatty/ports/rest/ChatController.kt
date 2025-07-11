package de.jkrech.tutorial.chatty.ports.rest

import de.jkrech.tutorial.chatty.application.ChatClientService
import de.jkrech.tutorial.chatty.application.SpeechService
import de.jkrech.tutorial.chatty.application.TranscriptionService
import de.jkrech.tutorial.chatty.domain.AudioResource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
@Suppress("unused")
class ChatController(
    private val transcriptionService: TranscriptionService,
    @Qualifier("openAiChatClientService") private val openAiChatClientService: ChatClientService,
    private val speechService: SpeechService
) {

    @PostMapping("/ai/chat")
    fun chat(@RequestPart("audioFile") audioFile: FilePart): Mono<ResponseEntity<ByteArrayResource>> {
        return AudioResource.from(audioFile)
            .subscribeOn(Schedulers.boundedElastic())
            .map { transcribe(it.value) }
            .map { chatClientGenerate(it) }
            .map { textToSpeech(it) }
            .map { toResponse(it) }
            .doOnError { throw ChatControllerException("Could not generate response", it.cause) }
    }

    private fun transcribe(audioFile: Resource): String {
        return transcriptionService.transcribe(audioFile)
    }

    private fun chatClientGenerate(promptMessage: String): String {
        return openAiChatClientService.generate(promptMessage)
    }

    private fun textToSpeech(promptMessage: String): ByteArray {
        return speechService.speech(promptMessage)
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