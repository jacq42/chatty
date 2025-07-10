package de.jkrech.tutorial.chatty.domain.ai.model

import org.springframework.ai.audio.transcription.AudioTranscription
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse
import org.springframework.ai.model.Model

class FakeTranscriptionModel: Model<AudioTranscriptionPrompt, AudioTranscriptionResponse> {

    override fun call(request: AudioTranscriptionPrompt?): AudioTranscriptionResponse? {
        return AudioTranscriptionResponse(AudioTranscription("Tell me a joke"))
    }

}