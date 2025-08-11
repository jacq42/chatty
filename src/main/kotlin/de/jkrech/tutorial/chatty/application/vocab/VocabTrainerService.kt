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
    @Qualifier("openAiChatClientWithMemory") private val chatClient: ChatClient,
) {

    @Value("classpath:/prompts/vocab-trainer-de-to-en.st")
    private val startPrompt: Resource? = null

    @Value("classpath:/prompts/vocab-trainer-de-to-en-answer.st")
    private val answerPrompt: Resource? = null

    private val sessions = mutableMapOf<String, VocabSession>()

    fun startSession(username: String): VocabTrainerResponse? {
        val vocabSession = VocabSession(
            username = username,
            remainingWords = mutableListOf("pile", "to pick up", "to type")
        )
        sessions[username] = vocabSession

        val systemMessage = createSystemMessage(vocabSession.remainingWords, username)
        val userMessage = UserMessage("I want to learn English.")
        val prompt = Prompt(listOf(systemMessage, userMessage))

        val response = try {
            chatClient.prompt(prompt).call().entity(VocabTrainerResponse::class.java)
        } catch (exc: Exception) {
            chatClient.prompt(prompt).call().content()?.let { parseResponseManually(it)}
        }

        response?.let { vocabResponse ->
            vocabSession.currentWord = vocabResponse.currentWord
            vocabSession.sentence = vocabResponse.sentence
            sessions[username] = vocabSession
        }

        return response
    }

    fun answer(username: String, message: String): VocabTrainerResponse? {
        val session = sessions[username] ?: throw IllegalStateException("No active session for user: $username")

        val systemMessage = createSystemAnswerMessage(session, message)
        val userMessage = UserMessage(message)
        val prompt = Prompt(listOf(systemMessage, userMessage))

        val response = try {
            chatClient.prompt(prompt).call().entity(VocabTrainerResponse::class.java)
        } catch (exc: Exception) {
            chatClient.prompt(prompt).call().content()?.let { parseResponseManually(it)}
        }

        response?.let { vocabResponse ->
            if (vocabResponse.pointsAwarded > 0 && session.remainingWords.isNotEmpty()) {
                session.remainingWords.removeAt(0)
                session.currentWord = vocabResponse.currentWord
                session.sentence = vocabResponse.sentence
            }
            session.totalPoints += vocabResponse.pointsAwarded
            sessions[username] = session
        }

        return response
    }

    private fun createSystemMessage(vocabularies: List<String>, username: String? = ""): Message? {
        val systemPromptTemplate = SystemPromptTemplate(startPrompt)
        val systemMessage = systemPromptTemplate.createMessage(mapOf(
            "words" to vocabularies.joinToString(","),
            "username" to username
        ))
        return systemMessage
    }

    private fun createSystemAnswerMessage(vocabSession: VocabSession, answer: String): Message? {
        val systemPromptTemplate = SystemPromptTemplate(answerPrompt)
        val systemMessage = systemPromptTemplate.createMessage(mapOf(
            "germanSentence" to vocabSession.sentence,
            "targetWord" to vocabSession.currentWord,
            "userAnswer" to answer
        ))
        return systemMessage
    }

    private fun parseResponseManually(rawResponse: String): VocabTrainerResponse? {
        return try {
            val jsonStart = rawResponse.indexOf("{")
            val jsonEnd = rawResponse.lastIndexOf("}") + 1

            if (jsonStart != -1 && jsonEnd > jsonStart) {
                val jsonString = rawResponse.substring(jsonStart, jsonEnd)
                ObjectMapper().readValue(jsonString, VocabTrainerResponse::class.java)
            } else {
                throw VocabTrainerResponseException("Invalid response format: $rawResponse")
            }
        } catch (e: Exception) {
            throw VocabTrainerResponseException("Invalid response format: $rawResponse", e)
        }
    }
}