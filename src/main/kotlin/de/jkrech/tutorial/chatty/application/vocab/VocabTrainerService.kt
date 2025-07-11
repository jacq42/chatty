package de.jkrech.tutorial.chatty.application.vocab

import com.fasterxml.jackson.databind.ObjectMapper
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
    @Qualifier("bedrockConverseChatClientWithMemory") private val chatClient: ChatClient,
    private val objectMapper: ObjectMapper
) {

    @Value("classpath:/prompts/vocab-trainer-de-to-en.st")
    private val vocabTrainerDeToEnPrompt: Resource? = null

    fun startSession(username: String): VocabTrainerResponse? {
        val systemMessage = createSystemMessage(username)
        val userMessage = UserMessage("I want to learn English.")
        val prompt = Prompt(listOf(systemMessage, userMessage))
        return chatClient.prompt(prompt).call().entity(VocabTrainerResponse::class.java)
    }

    fun answer(message: String): VocabTrainerResponse? {
        val systemMessage = createSystemMessage()
        val userMessage = UserMessage(message)
        val prompt = Prompt(listOf(systemMessage, userMessage))
        return chatClient.prompt(prompt).call().entity(VocabTrainerResponse::class.java)
    }

    private fun createSystemMessage(username: String? = ""): Message? {
        val vocabularies = listOf("Teller", "Tisch", "Tasse")
        val systemPromptTemplate = SystemPromptTemplate(vocabTrainerDeToEnPrompt)
        val systemMessage = systemPromptTemplate.createMessage(mapOf(
            "words" to vocabularies.joinToString(","),
            "username" to username
        ))
        return systemMessage
    }
}