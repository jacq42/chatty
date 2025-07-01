package de.jkrech.tutorial.chatty.application

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {

//    @Bean
//    fun runner(builder: ChatClient.Builder): CommandLineRunner {
//        return CommandLineRunner { args: Array<String?>? ->
//            val chatClient = builder.build()
//            val response = chatClient.prompt("Tell me a joke").call().content()
//            println(response)
//        }
//    }

     @Bean
     fun chatClientBuilder(@Qualifier("bedrockProxyChatModel") chatModel: ChatModel): ChatClient.Builder {
         return ChatClient.builder(chatModel)
     }
}