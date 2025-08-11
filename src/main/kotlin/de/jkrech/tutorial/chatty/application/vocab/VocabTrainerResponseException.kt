package de.jkrech.tutorial.chatty.application.vocab

class VocabTrainerResponseException: RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable?) : super(message, cause)
}
