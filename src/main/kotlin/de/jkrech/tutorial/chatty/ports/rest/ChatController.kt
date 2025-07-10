package de.jkrech.tutorial.chatty.ports.rest

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
@Suppress("unused")
class ChatController @Autowired constructor(builder: ChatClient.Builder) {

    private val chatClient: ChatClient = builder.build()

    @GetMapping("/ai/generate")
    fun generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") message: String): Mono<Map<String, String>>   {
        return chat(message)
            .map { optionalChatResponse ->
                mapOf("generation" to (optionalChatResponse ?: ""))
            }
    }

    private fun chat(promptMessage: String): Mono<String?> {
        return Mono.fromCallable {
            this.chatClient.prompt(promptMessage).call().content()
        }.subscribeOn(Schedulers.boundedElastic())
    }

    @GetMapping("/ai/generateStream")
    fun generateStream(
        @RequestParam(
            value = "message",
            defaultValue = "Tell me a joke"
        ) message: String
    ): Flux<String> {
        return this.chatClient.prompt(message).stream().content()
    }
}