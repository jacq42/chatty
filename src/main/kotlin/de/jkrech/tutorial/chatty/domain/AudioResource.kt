package de.jkrech.tutorial.chatty.domain

import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Mono
import java.io.File

@JvmInline
value class AudioResource(private val input: Resource) {

    val value: Resource
        get() = input

    companion object {
        fun from(audioFile: FilePart): Mono<AudioResource> {
            val tempFile = File.createTempFile("mic-audio", ".webm")
            return audioFile.transferTo(tempFile)
                .then(Mono.just(AudioResource(FileSystemResource(tempFile))))
                .doOnError { tempFile.delete() }
        }
    }
}