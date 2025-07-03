package de.jkrech.tutorial.chatty.ports.rest

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions
import org.springframework.ai.openai.api.OpenAiAudioApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
class ChatController @Autowired constructor(builder: ChatClient.Builder) {

    private val chatClient: ChatClient = builder.build()

    @Autowired
    private var transcriptionModel: OpenAiAudioTranscriptionModel? = null

    @Value("classpath:/speech/tell-me-a-joke.flac")
    private val audioFile: Resource? = null

    @GetMapping("/ai/generate")
    fun generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") message: String): Mono<Map<String, String>>   {
        return chat(message)
            .map { optionalChatResponse ->
                mapOf("generation" to (optionalChatResponse ?: ""))
            }
    }

    @GetMapping("/ai/generateWithTranscription")
    fun generateWithTranscription(@RequestParam(value = "message", defaultValue = "Tell me a fact about the weather") message: String): Mono<Map<String, String>> {
        return transcribe()
            .flatMap { optionalTranscription ->
                chat(optionalTranscription ?: message)
            }
            .map { optionalChatResponse ->
                mapOf("generation" to (optionalChatResponse ?: ""))
            }
            .doOnError {
                throw ChatControllerException("Could not generate response", it.cause)
            }
    }

    private fun chat(promptMessage: String): Mono<String?> {
        return Mono.fromCallable {
            this.chatClient.prompt(promptMessage).call().content()
        }.subscribeOn(Schedulers.boundedElastic())
    }

    private fun transcribe(): Mono<String?> {
        val transcriptionOptions: OpenAiAudioTranscriptionOptions? = OpenAiAudioTranscriptionOptions.builder()
            .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
            .temperature(0f)
            .build()
        val transcriptionRequest = AudioTranscriptionPrompt(
            this.audioFile,
            transcriptionOptions
        )
        return Mono.fromCallable { transcriptionModel?.call(transcriptionRequest)?.result?.output }
            // boundedElastic scheduler lets run blocking calls in a separate thread pool
            .subscribeOn(Schedulers.boundedElastic())

    }

    @GetMapping("/ai/generateStream")
    fun generateStream(
        @RequestParam(
            value = "message",
            defaultValue = "Tell me a joke"
        ) message: String
    ): Flux<String> {
        return this.chatClient.prompt(message).stream().content()
    }
}