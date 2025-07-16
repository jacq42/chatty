package de.jkrech.tutorial.chatty.ports.rest

import de.jkrech.tutorial.chatty.application.vocab.VocabTrainerResponse
import de.jkrech.tutorial.chatty.application.vocab.VocabTrainerService
import de.jkrech.tutorial.chatty.application.vocab.currentVocab
import de.jkrech.tutorial.chatty.application.vocab.messageAndFeedback
import de.jkrech.tutorial.chatty.ports.rest.exceptions.VocabTrainerControllerException
import org.slf4j.LoggerFactory
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

    private final val logger = LoggerFactory.getLogger(VocabTrainerController::class.java)

    @PostMapping("/ai/vocab/start")
    fun startSession(
        @RequestPart(value = "username") username: String
    ): Mono<Map<String, String>>   {
        return Mono.fromCallable { vocabTrainerService.startSession(username) }
            .subscribeOn(Schedulers.boundedElastic())
            .doOnNext(::logResponseFromService)
            .map(::mapResult)
            .doOnError(BedrockRuntimeException::class.java) { exception ->
                if (exception.statusCode() == 403) {
                    throw SecurityException(exception.message)
                }
                throw VocabTrainerControllerException("Could not start vocab trainer", exception)
            }
    }

    @PostMapping("/ai/vocab/continue")
    fun continueSession(
        @RequestPart(value = "answer") message: String
    ): Mono<Map<String, String>>   {
        return Mono.fromCallable { vocabTrainerService.answer(message) }
            .subscribeOn(Schedulers.boundedElastic())
            .doOnNext(::logResponseFromService)
            .map(::mapResult)
            .doOnError(BedrockRuntimeException::class.java) { exception ->
                if (exception.statusCode() == 403) {
                    throw SecurityException(exception.message)
                }
                throw VocabTrainerControllerException("Could not continue vocab trainer", exception)
            }
    }

    private fun mapResult(optionalChatResponse: VocabTrainerResponse?): Map<String, String> {
        return mapOf(
                "result" to optionalChatResponse.messageAndFeedback(),
                "currentVocab" to optionalChatResponse.currentVocab()
            )
    }

    private fun logResponseFromService(optionalChatResponse: VocabTrainerResponse?) {
        logger.info("response: $optionalChatResponse")
    }
}