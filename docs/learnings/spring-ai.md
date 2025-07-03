# Spring AI

* [Spring AI reference](https://docs.spring.io/spring-ai/reference/index.html)
* [Spring AI examples](https://github.com/spring-projects/spring-ai-examples)

## Chat

* Configure chat model via properties
  * See examples for openAI and AWS bedrock in [application.yml](../../src/main/resources/application.yml)
* Inject ChatClient and call `chatClient.prompt(..)`
  * See example in [ChatController](../../src/main/kotlin/de/jkrech/tutorial/chatty/ports/html/ChatController.kt)

## Transcription with OpenAI

* [Documentation](https://docs.spring.io/spring-ai/reference/api/audio/transcriptions/openai-transcriptions.html)
* Current error: 429: You exceeded your current quota, please check your plan and billing details.
* Next step: Billing plan for OpenAI

## Run locally

add as environment variables: AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_REGION, OPENAI_API_KEY
