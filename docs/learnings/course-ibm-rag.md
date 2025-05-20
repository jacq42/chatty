# Courses about RAG

## Course #1: Summarize private documents using RAG, LangChain, and LLMs

[Cognitive Class course #1](https://apps.cognitiveclass.ai/learning/course/course-v1:IBMSkillsNetwork+GPXX0FVDEN+v1/block-v1:IBMSkillsNetwork+GPXX0FVDEN+v1+type@sequential+block@get_started/block-v1:IBMSkillsNetwork+GPXX0FVDEN+v1+type@vertical+block@lets_do_it)
Lab is done with [LangChain](https://python.langchain.com/docs/introduction/)

### What is RAG?

- retrieval-augmented generation
- augmented LLM knowledge with additional data: PDFs, websites, databases, APIs
- can be private data or public data after model's cut-off date

### RAG architecture

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

### Course Lab

See [RAG course IBM notebook](notebooks/RAG-Course-IBM.ipynb)

## Course #2: RAG with LlamaIndex - Build a retrieval agent using LLMs

[Cognitive Class course #2](https://apps.cognitiveclass.ai/learning/course/course-v1:IBMSkillsNetwork+GPXX0TQPEN+v1/block-v1:IBMSkillsNetwork+GPXX0TQPEN+v1+type@sequential+block@get_started/block-v1:IBMSkillsNetwork+GPXX0TQPEN+v1+type@vertical+block@lets_do_it)
Lab is done with [LlamaIndex](https://docs.llamaindex.ai/en/stable/)

### RAG stages

1. Loading:
   - bringing your data into the workflow
   - LlamaHub offers a wide range of connectors
2. Indexing:
   - creating a data structure for efficient querying
   - generate vector embeddings (= numerical representations that capture the meaning of the data)
   - plus metadata strategies to ensure accurate and contextually relevant data retrieval
3. Storing:
   - save index with associated metadata 
4. Querying:
   - multiple ways to utilize LLMs and LlamaIndex for querying
   - can include sub-queries, multi-step queries, hybrid approaches 
5. Evaluation:
   - essential stage
   - how effective is your approach compared to others or when adjustments are made
   - objective metrics to access accuracy, fidelity, speed

### Course Lab

See [RAG course IBM notebook](notebooks/RAG-course-IBM-LlamaIndex.ipynb)

