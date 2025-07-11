package de.jkrech.tutorial.chatty.ports.rest

import de.jkrech.tutorial.chatty.application.ChatClientService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
@Suppress("unused")
class ChatController (
    @Qualifier("openAiChatClientService") private val openAiChatClientService: ChatClientService
) {

    @GetMapping("/ai/generate")
    fun generate(
        @RequestParam(value = "message", defaultValue = "Tell me a joke") message: String
    ): Mono<Map<String, String>>   {
        return chatClientGenerate(message)
            .map { optionalChatResponse ->
                mapOf("generation" to (optionalChatResponse ?: ""))
            }
    }

    @GetMapping("/ai/generateStream")
    fun generateStream(
        @RequestParam(value = "message", defaultValue = "Tell me a joke") message: String
    ): Flux<String> {
        return openAiChatClientService.generateStream(message)
    }

    private fun chatClientGenerate(promptMessage: String): Mono<String?> {
        return Mono.fromCallable { openAiChatClientService.generate(promptMessage) }
            .subscribeOn(Schedulers.boundedElastic())
    }
}