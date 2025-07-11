package de.jkrech.tutorial.chatty.application.vocab

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.SystemPromptTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class VocabTrainerService(
    @Qualifier("bedrockConverseChatClient") private val chatClient: ChatClient
) {

    @Value("classpath:/prompts/vocab-trainer-de-to-en.st")
    private val vocabTrainerDeToEnPrompt: Resource? = null

    fun start(username: String): String? {
        val vocabularies = listOf("Teller", "Tisch", "Tasse")
        val systemPromptTemplate = SystemPromptTemplate(vocabTrainerDeToEnPrompt)
        val systemMessage = systemPromptTemplate.createMessage(mapOf(
            "words" to vocabularies.joinToString(","),
            "username" to username
        ))
        val userMessage = UserMessage("I want to learn English.")
        val prompt = Prompt(listOf<Message?>(systemMessage, userMessage))

        return chatClient.prompt(prompt).call().content()
    }
}