# LLM

Source: [Hugging Face](https://huggingface.co/learn/llm-course/chapter0/1?fw=pt)

## Setup

* Use [Jypiter Notebook](https://jupyter.org/install)
* All notebooks can be found in `docs/learnings/notebooks`
* For working with a notebook just:
  * Start terminal
  * Go to `docs/learnings/notebooks`
  * Type `jupyter notebook` and select the notebook you want to work with

* Install [PyTorch](https://pytorch.org/get-started/locally/):

```bash
pip3 install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cpu
```

## Transformer models

* NLP (Natural Language Processing)
  * understand, interpret and generate human language
  * understand the context of the words
  * is not limited to written text: transcript audio sample, describing images

* LLMs (Large Language Models)
  * subset of NPL models
  * massive size, extensive training data
  * minimal task specific training

* Example notebook can be found here: [Transformers](notebooks/Transformers.ipynb)
* Tutorial for pipelines: [from HuggingFace](https://github.com/huggingface/transformers/blob/main/docs/source/en/pipeline_tutorial.md)
* Find a model: [from HuggingFace](https://huggingface.co/models?pipeline_tag=token-classification&language=en&sort=trending)

### Transformer architecture

* Encoder-only (BERT): 
  * understand context from both directions
  * for task that need a deep understanding of the text
  * examples: text classification, named entity recognition
* Decoder-only (GPT, Llama):
  * process text from left to right
  * good at text generation
  * examples: text generation, complete sentences
* Encoder-decoder (BART, T5):
  * combine both approaches
  * encoder for understanding the input
  * decoder for generating the output
  * examples: translation, summarization, question answering

### Inference

* the process of using a trained model to generate human like text from a given input prompt
* attention:
  * is the ability to understand the context and generate coherent text
  * ability to focus on relevant parts of the input = attention
  * example: The capital of France is ... 
  * capital and France are crucial to predict the answer Paris
* context length = maximum number of tokens the model can consider at once when generating the response
  * different models are designed with different context lengths to balance capability and efficiency
* prompting = pass information to models in a structured way
  * careful design of the prompt makes it easier to guide the generation of the model toward the desired output

* next: https://huggingface.co/learn/llm-course/chapter1/8?fw=pt

* TODO: https://huggingface.co/docs/transformers/tasks/asr