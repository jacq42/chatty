package de.jkrech.tutorial.chatty.ports.rest.exceptions

class ChatControllerException: RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable?) : super(message, cause)
}