# Spring AI

* [Spring AI reference](https://docs.spring.io/spring-ai/reference/index.html)
* [Spring AI examples](https://github.com/spring-projects/spring-ai-examples)

## Chat

* Configure chat model via properties
  * See examples for openAI and AWS bedrock in [application.yml](../../src/main/resources/application.yml)
* There is an auto configuration for the chat model, that depends on the properties.
  * See [ChatModelConfiguration](../../src/main/kotlin/de/jkrech/tutorial/chatty/application/ChatModelConfiguration.kt)
* Use of the chat model with 
  * See example in [ChatController](../../src/main/kotlin/de/jkrech/tutorial/chatty/ports/rest/ChatController.kt)

## Transcription with OpenAI

* [Documentation](https://docs.spring.io/spring-ai/reference/api/audio/transcriptions/openai-transcriptions.html)
* Current error: 429: You exceeded your current quota, please check your plan and billing details.
* Next step: Billing plan for OpenAI

## Text-to-Speech with OpenAI

* [Documentation](https://docs.spring.io/spring-ai/reference/api/audio/speech/openai-speech.html)
* Current error: 429: You exceeded your current quota, please check your plan and billing details.
* Next step: Billing plan for OpenAI

## Run locally

1. Add environment variables: 
   * AWS_ACCESS_KEY_ID
   * AWS_SECRET_ACCESS_KEY
   * AWS_REGION
   * OPENAI_API_KEY
2. Run the application with `./gradlew bootRun`

## Architecture

* use hexagonal architecture
* 