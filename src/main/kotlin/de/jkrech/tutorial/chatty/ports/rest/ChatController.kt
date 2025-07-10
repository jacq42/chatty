package de.jkrech.tutorial.chatty.ports.rest

import de.jkrech.tutorial.chatty.application.ChatClientService
import de.jkrech.tutorial.chatty.application.TranscriptionService
import de.jkrech.tutorial.chatty.domain.AudioResource
import org.springframework.core.io.Resource
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
    private val chatClientService: ChatClientService
) {

    @PostMapping("/ai/chat")
    fun chat(@RequestPart("audioFile") audioFile: FilePart): Mono<Map<String, String>> {
        return AudioResource.from(audioFile)
            .flatMap { audioResource ->
                transcribe(audioResource.value)
                    .flatMap { optionalResult ->
                        chatClientGenerate(optionalResult ?: "")
                    }
                    .map { optionalChatResponse ->
                        mapOf("result" to (optionalChatResponse ?: ""))
                    }
                    .doOnError { throw ChatControllerException("Could not generate response", it.cause) }
                    }
    }

    private fun transcribe(audioFile: Resource): Mono<String?> {
        return Mono.fromCallable { transcriptionService.transcribe(audioFile) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    private fun chatClientGenerate(promptMessage: String): Mono<String?> {
        return Mono.fromCallable { chatClientService.generate(promptMessage) }
            .subscribeOn(Schedulers.boundedElastic())
    }
}