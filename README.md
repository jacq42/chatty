# chatty

[100daysOfCode](https://github.com/Kallaway/100-days-of-code) project for developing an english speaking chatbot to practice english speaking skills.
The bot is using AI to generate responses. It uses the vocabularies and grammar of the units in the english book.

* Have a look at the [rules](100daysOfCode/rules.md).
* Here you can find the [log](100daysOfCode/log.md) of the project.

## Purpose

Chatty is an english speaking chatbot. 

## Components

* Voice Interface: frontend to communicate with the assistant 
* -> STT (Speech to Text): converts audio to text
* -> NLU (Natural Language Understanding): converts text to intents and entities
* -> NLG (Natural Language Generation): converts intents to text
* -> TTS (Text to Speech): converts text to audio
* -> Voice Interface: give the response to the user

## Frameworks

* [LlamaIndex](https://docs.llamaindex.ai/en/stable/getting_started/concepts/)
* [LangChain](https://python.langchain.com/docs/introduction/)
* [LiveKit](https://docs.livekit.io/home/)


## Links

* Just the article, not the outdated(!) frameworks: [Voice assistant](https://medium.com/rasa-blog/how-to-build-a-voice-assistant-with-open-source-rasa-and-mozilla-tools-c05c4ec698c6)
* Courses at [Cognitive Class](https://courses.cognitiveclass.ai/)