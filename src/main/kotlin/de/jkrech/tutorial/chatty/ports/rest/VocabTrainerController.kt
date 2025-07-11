package de.jkrech.tutorial.chatty.ports.rest

import de.jkrech.tutorial.chatty.application.vocab.VocabTrainerService
import de.jkrech.tutorial.chatty.ports.rest.exceptions.VocabTrainerControllerException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import software.amazon.awssdk.services.bedrockruntime.model.BedrockRuntimeException

@RestController
@Suppress("unused")
class VocabTrainerController (
    private val vocabTrainerService: VocabTrainerService
) {

    @PostMapping("/ai/vocab/start")
    fun startVocab(
        @RequestPart(value = "username") username: String
    ): Mono<Map<String, String>>   {
        return startToTrain(username)
            .map { optionalChatResponse ->
                mapOf("result" to (optionalChatResponse ?: ""))
            }
            .doOnError(BedrockRuntimeException::class.java) { exception ->
                if (exception.statusCode() == 403) {
                    throw SecurityException(exception.message)
                }
                throw VocabTrainerControllerException("Could not start vocab trainer", exception)
            }
    }

    private fun startToTrain(username: String): Mono<String?> {
        return Mono.fromCallable { vocabTrainerService.start(username) }
            .subscribeOn(Schedulers.boundedElastic())
    }
}