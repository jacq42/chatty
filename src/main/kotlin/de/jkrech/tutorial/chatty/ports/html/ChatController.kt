package de.jkrech.tutorial.chatty.ports.html

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.util.Map


@RestController
class ChatController @Autowired constructor(builder: ChatClient.Builder) {

    private val chatClient: ChatClient

    init {
        this.chatClient = builder.build()
    }

    @GetMapping("/ai/generate")
    fun generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") message: String): MutableMap<*, *> {
        return Map.of<String?, String?>("generation", this.chatClient.prompt(message).call().content())
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