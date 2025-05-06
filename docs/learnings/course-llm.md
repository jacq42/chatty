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