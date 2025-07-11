package de.jkrech.tutorial.chatty.application

import reactor.core.publisher.Flux

interface ChatClientService {

    fun generate(message: String): String
    fun generateStream(message: String): Flux<String>
}