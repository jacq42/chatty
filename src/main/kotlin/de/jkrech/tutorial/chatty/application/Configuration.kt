package de.jkrech.tutorial.chatty.application

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestClient

@Configuration
class Configuration {

//     // AWS Bedrock model
//     @Bean
//     fun chatClientBuilder(@Qualifier("bedrockProxyChatModel") chatModel: ChatModel): ChatClient.Builder {
//         return ChatClient.builder(chatModel)
//     }

    // OpenAI model
    @Bean
    fun chatClientBuilder(@Qualifier("openAiChatModel") chatModel: ChatModel): ChatClient.Builder {
        return ChatClient.builder(chatModel)
    }

    // TODO How to configure this via application.properties?
    @Bean
    fun restClientBuilder(): RestClient.Builder {
        val requestFactory = SimpleClientHttpRequestFactory().apply {
            setConnectTimeout(60000)      // 60 Sekunden Connect-Timeout
            setReadTimeout(300000)        // 5 Minuten Read-Timeout
        }

        return RestClient.builder()
            .requestFactory(requestFactory)
    }

}