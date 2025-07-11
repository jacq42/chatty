package de.jkrech.tutorial.chatty.application.vocab

data class VocabTrainerResponse(
    val status: String, // "active" oder "completed"
    val currentWord: String?,
    val message: String,
    val feedback: String?,
    val isCorrect: Boolean,
    val remainingWords: Int
)