# Building AI voice agents

Source: https://learn.deeplearning.ai/courses/building-ai-voice-agents-for-production/

## What is a voice agent?

- combine speech and reasoning abilities of foundation models to deliver real-time, human-like voice interactions
- use cases:
  - improve learning: guide personalized skill development, conduct interviews
  - handle customer service voice calls: bookings, sales, insurance
  - improve accessibility in medical and talk therapy apps

### Structure

- Input: User Voice Query
  - VAD: Voice Activity Detection: detecting presence/absence of human speech
  - EOU: End of Turn/Utterance Detection: detecting when a user has finished speaking
- SST: Speech to Text (= ASR: Automatic Speech Recognition)
- LLM or Agentic LLM Workflow
- TTS: Text to Speech (Speech Synthesis)
- Output: System Voice Response

- there are different models for SST, LLM and TTS
- chosen model depends on the use case
  - is medical vocabulary needed -> specialized SST
  - booking a table in restaurant needs real time information

### Low Latency audio streaming

- latency of human interactions:
  - human expects a response in avg 236 ms
- latency of voice agent interactions:
  - best: 540 ms, worst: 1.6s
  
- Real Time Peer-to-Peer communication
  - WebRTC: open source project providing browsers and mobile apps with real time communication via APIs
    - built on UDP
    - designed for streaming audio and video
  - WebSocket network communication protocol
    - built on TCP
  - LiveKit platform

## Frameworks

- [LiveKit](https://docs.livekit.io/home/)
- [ElevenLabs](https://elevenlabs.io/de)

- the needed components from live demo need paid plans (openai, deepgram)
- are there open source alternatives?

- Docs: https://github.com/livekit/agents/blob/6019c43ac6a60bebdb8437aef11e8ef64042b94b/examples/voice-assistant/function_calling.py

Amazon Bedrock:
- Tutorial: https://docs.aws.amazon.com/bedrock/latest/userguide/getting-started.html
- Model Support:
- Model freigeben: https://eu-central-1.console.aws.amazon.com/bedrock/home?region=eu-central-1#/overview
- Fehlermeldung:
```
livekit.agents._exceptions.APIConnectionError: 
aws bedrock llm: 
error generating content: 
An error occurred (ValidationException) when calling the ConverseStream operation: 
Invocation of model ID amazon.nova-lite-v1:0 with on-demand throughput isn’t supported. Retry your request with the ID or ARN of an inference profile that contains this model.
```
- Lösung: https://repost.aws/questions/QUuisypRIxS5aVwTDznjyeLQ/meta-llama3-2-11b-instruct-v1-0-not-supported-for-on-demand-throughput