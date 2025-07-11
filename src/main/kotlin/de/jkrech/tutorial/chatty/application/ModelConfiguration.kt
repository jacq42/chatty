package de.jkrech.tutorial.chatty.application

import de.jkrech.tutorial.chatty.domain.ai.model.FakeSpeechModel
import de.jkrech.tutorial.chatty.domain.ai.model.FakeTranscriptionModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse
import org.springframework.ai.bedrock.converse.BedrockProxyChatModel
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.model.Model
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.audio.speech.SpeechModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestClient
import java.time.Duration

@AutoConfiguration(beforeName = ["org.springframework.ai.model.chat.client.autoconfigure.ChatClientAutoConfiguration"])
class ModelConfiguration {

    final val logger: Logger = LoggerFactory.getLogger(ModelConfiguration::class.java)

    @Bean
    fun openAiChatClient(chatModel: OpenAiChatModel): ChatClient {
        return ChatClient.create(chatModel)
    }

    @Bean
    fun bedrockConverseChatClient(chatModel: BedrockProxyChatModel): ChatClient {
        return ChatClient.create(chatModel)
    }

    @Bean
    fun bedrockConverseChatClientWithMemory(chatModel: BedrockProxyChatModel, chatMemory: ChatMemory): ChatClient {
        return ChatClient.builder(chatModel)
            .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
            .build();
    }

    @Bean
    fun restClientBuilder(
        @Value("\${spring.ai.openai.http-client.connection-timeout:60s}") conTimeout: Duration,
        @Value("\${spring.ai.openai.http-client.read-timeout:2m}") readTimeout: Duration
    ): RestClient.Builder {
        val requestFactory = SimpleClientHttpRequestFactory().apply {
            setConnectTimeout(conTimeout)
            setReadTimeout(readTimeout)
        }

        return RestClient.builder()
            .requestFactory(requestFactory)
    }

    @Bean
    @Primary
    @ConditionalOnProperty(
        prefix = "spring.ai.model.audio",
        name = ["transcription"],
        havingValue = "fake",
        matchIfMissing = false
    )
    fun fakeTranscriptionModel(): Model<AudioTranscriptionPrompt, AudioTranscriptionResponse> {
        logger.info("Configured transcription model: Fake")
        return FakeTranscriptionModel()
    }

    @Bean
    @Primary
    @ConditionalOnProperty(
        prefix = "spring.ai.model.audio",
        name = ["speech"],
        havingValue = "fake",
        matchIfMissing = false
    )
    fun fakeSpeechModel(): SpeechModel  {
        logger.info("Configured speech model: Fake")
        return FakeSpeechModel()
    }
}