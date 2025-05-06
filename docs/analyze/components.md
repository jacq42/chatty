# Find the right components

* Components from ChatGPT response:

| Component                                | Purpose                       | Open Source Tools / Frameworks                                                                                                                                                                           | Notes                                                                    |
| ---------------------------------------- | ----------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------ |
| **STT (Speech to Text)**                 | Converts spoken audio to text | - [Vosk](https://alphacephei.com/vosk/)  <br> - [Coqui STT](https://github.com/coqui-ai/STT) <br> - [Whisper by OpenAI](https://github.com/openai/whisper)                                               | Whisper is powerful but heavier; Vosk is lightweight and offline-ready.  |
| **TTS (Text to Speech)**                 | Converts text to spoken audio | - [Coqui TTS](https://github.com/coqui-ai/TTS) <br> - [Festival](http://www.cstr.ed.ac.uk/projects/festival/) <br> - [ESPnet-TTS](https://github.com/espnet/espnet)                                      | Coqui TTS is high-quality and modern. Festival is older, lightweight.    |
| **NLU (Natural Language Understanding)** | Interprets user input         | - [Rasa NLU](https://rasa.com/) <br> - [spaCy](https://spacy.io/) <br> - [Snips NLU](https://github.com/snipsco/snips-nlu)                                                                               | Rasa is comprehensive and modular. Snips is lightweight and local.       |
| **NLG (Natural Language Generation)**    | Generates human-like text     | - [Transformers (Hugging Face)](https://huggingface.co/transformers/) <br> - [DialoGPT](https://huggingface.co/microsoft/DialoGPT-medium) <br> - [Rasa Responses](https://rasa.com/docs/rasa/responses/) | DialoGPT can generate fluent replies. Rasa supports templated responses. |
| **Dialogue Management**                  | Manages conversation flow     | - [Rasa Core](https://rasa.com/) <br> - [Botpress](https://botpress.com/) <br> - [Microsoft Bot Framework (partially OSS)](https://github.com/microsoft/botframework-sdk)                                | Rasa handles state + rules well. Botpress offers a visual flow editor.   |

* Coqui TTS is too old

* [Nvidia NeMo](https://docs.nvidia.com/nemo-framework/user-guide/latest/automodel/index.html)
* [Hugging Face](https://huggingface.co)
  * [LLM Course](https://huggingface.co/learn/llm-course/chapter1/1) 
  * [Agents Course](https://huggingface.co/learn/agents-course/unit0/introduction) 
  * [Audio Course](https://huggingface.co/learn/audio-course/chapter0/introduction)
