package de.jkrech.tutorial.chatty.application.vocab

fun VocabTrainerResponse?.messageAndFeedback(): String {
    if (this == null) return ""

    return listOf(feedback, message)
        .filterNot { it.isNullOrBlank() }
        .joinToString(" ")
}

fun VocabTrainerResponse?.currentVocab(): String {
    if (this == null) return ""

    return this.nextWord ?: this.currentWord ?: ""
}