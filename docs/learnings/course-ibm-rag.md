# Summarize private documents using RAG, LangChain, and LLMs

[Cognitive Class course](https://apps.cognitiveclass.ai/learning/course/course-v1:IBMSkillsNetwork+GPXX0FVDEN+v1/block-v1:IBMSkillsNetwork+GPXX0FVDEN+v1+type@sequential+block@get_started/block-v1:IBMSkillsNetwork+GPXX0FVDEN+v1+type@vertical+block@lets_do_it)
Lab is done with [LangChain](https://python.langchain.com/docs/introduction/)

## What is RAG?

- retrieval-augmented generation
- augmented LLM knowledge with additional data
- can be private data or public data after model's cut-off date

## RAG architecture

- two main components: indexing and retrieval & generation
- indexing:
  - pipeline for ingesting and indexing data from a source 
  - happens offline 
  - steps:
    - load the data with DocumentLoaders
    - split: break large documents into smaller chunks with TextSplitter
    - store: store and index the chunks for searching by VectorStore or Embeddings
- retrieval & generation:
  - RAG chain takes the user query and retrieves relevant information from index
  - passes both to the model
  - steps:
    - retrieve relevant splits from storage
    - generate a response by passing the question and retrieved splits to the LLM

## Lab

See [RAG course IBM notebook](notebooks/RAG-Course-IBM.ipynb)