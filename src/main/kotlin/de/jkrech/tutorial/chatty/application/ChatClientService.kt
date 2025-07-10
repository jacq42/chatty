package de.jkrech.tutorial.chatty.application

import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ChatClientService (builder: ChatClient.Builder) {

    private val chatClient: ChatClient = builder.build()

    fun generate(message: String): String? {
        return chatClient.prompt(message).call().content()
    }

    fun generateStream(message: String): Flux<String> {
        return chatClient.prompt(message).stream().content()
    }
}