package de.jkrech.tutorial.chatty.application.vocab

data class VocabTrainerResponse(
    val status: String, // "waiting for answer" oder "end of conversation"
    val currentWord: String?,
    val nextWord: String?,
    val message: String,
    val feedback: String?,
    val correctAnswer: Boolean?,
    val remainingWords: Int
)

// TODO: correctAnswer auswerten, Ende auswerten