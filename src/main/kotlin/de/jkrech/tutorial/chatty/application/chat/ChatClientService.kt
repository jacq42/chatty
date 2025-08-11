package de.jkrech.tutorial.chatty.application.chat

import reactor.core.publisher.Flux

interface ChatClientService {

    fun generate(message: String): String
    fun generateStream(message: String): Flux<String>
}