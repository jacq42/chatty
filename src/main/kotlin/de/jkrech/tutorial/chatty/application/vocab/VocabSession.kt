package de.jkrech.tutorial.chatty.application.vocab

data class VocabSession(
    val username: String,
    val remainingWords: MutableList<String>,
    var totalPoints: Int = 0,
    var currentWord: String? = null,
    var sentence: String? = null
)
