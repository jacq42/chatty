package de.jkrech.tutorial.chatty.application

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
@Qualifier("bedrockConverseChatClientService")
class ChatClientBedrockConverseService (
    @Qualifier("bedrockConverseChatClient") private val chatClient: ChatClient
): ChatClientService {

    override fun generate(message: String): String {
        return chatClient.prompt(message).call().content() ?: ""
    }

    override fun generateStream(message: String): Flux<String> {
        return chatClient.prompt(message).stream().content()
    }
}