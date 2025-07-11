# AI Agents

Source: [Hugging Face Agents Course](https://huggingface.co/learn/agents-course/unit0/introduction)

## Unit 0

Why do I need [Ollama](https://github.com/ollama/ollama/blob/main/README.md#quickstart)?
* allows you to run models locally

See also [Tools](lessons-learned.md#local-llms)

## Unit 1

### What is an agent?

> An  Agent is a system that leverages an AI model to interact with its environment in order to achieve a user-defined objective. It combines reasoning, planning, and the execution of actions (often via external tools) to fulfill tasks.

- Has two parts:
  - Brain (AI model): handles reasoning and planning. Decides which action to take based on the situation.
  - Body (Capability and Tools): represents everything the agent is equipped to do.

- Spectrum of "Agency":

| Agency Level | Description | What that’s called |	Example pattern |
| -------- | ------- | -------- | ------- |
| ☆☆☆ | Agent output has no impact on program flow | Simple processor | process_llm_output(llm_response) |
| ★☆☆ |	Agent output determines basic control flow | Router | if llm_decision(): path_a() else: path_b() |
| ★★☆ |	Agent output determines function execution | Tool caller | run_function(llm_chosen_tool, llm_chosen_args) |
| ★★★ |	Agent output controls iteration and program continuation | Multi-step Agent | while llm_should_continue(): execute_next_step() |
| ★★★ |	One agentic workflow can start another agentic workflow | Multi-Agent | if llm_trigger(): execute_agent() |

- Types of models:
  - LLMs: takes text as input and generates text as output
  - VLM (Vision Language Model): understands images as input

- An agent can perform any task we implement via Tools to complete Actions
- The design of the Tools is very important and has a great impact on the quality of our Agent.
- Actions is not Tools.

### What are LLMs?

- Understands and generates human language.
- Built on the transformer architecture.
- Three types:
  - Encoder-only: Processes input text and generates a representation.
    - text classification, semantic search, named entity recognition
  - Decoder-only: Generates text based on input.
    - text generation, chatbots, code generation
  - Encoder-decoder (Sequence-to-sequence): Processes input and generates output
    - translation, summarization, question answering
- They predict the next token based on the previous tokens.
- Each model has special tokens to indicate the start and end of a sequence or message.
- A key aspect is attention: some words in a sentence are more important than others
  - Identifying the most relevant words to predict the next token has proven to be incredible effective.
- Context length = maximum number of tokens a model can process = maximum attention span
- Input sequence to LLM is a prompt.
  - Careful design of the prompt makes it easier for the model to find the most relevant words
- Pre-training with large datasets (unsupervised learning)
- Fine-tuning with specific datasets
- Use of LLMs:
  - Locally: Ollama
  - Via API: Hugging Face Serverless Inference API
- Base model vs. Instruct model::
  - Base model is trained on raw text to predict the next token.
  - Instruct model is fine-tuned to follow instructions and engage in conversations.

### Messages

- Chat templates act as a bridge between conversational messages and the specific formatted requirements of the underlying model.
  - Structure the communication between the user and the agent.
  - Each LLM uses its own EOS token, different formatting rules and delimiters.
- System messages (System prompts):
  - Define how the model should behave.
  - Serve as persistent instruction, guiding every interaction.
  - Example:
  ```
    system_message = {
      "role": "system",
      "content": "You are a professional customer service agent. Always be polite, clear, and helpful."
  }
  ```
  - Gives information about available tools
  - Provides instruction about how to format the actions to take
  - Includes guidelines on how the thought process should be segmented
- Conversations: User and assistant messages
  - A conversation consists of alternating messages between a human (user) and a model (assistant)
  - Chat templates contains a conversation history (storing previous exchanges) -> more coherent multi-turn conversations
  - Example:
  ```
  conversation = [
    {"role": "user", "content": "I need help with my order"},
    {"role": "assistant", "content": "I'd be happy to help. Could you provide your order number?"},
    {"role": "user", "content": "It's ORDER-123"},
  ]
  ```
  - is converted into this prompt (for SmolLM2 model): 
  ```
  <|im_start|>system
  You are a helpful AI assistant named SmolLM, trained by Hugging Face<|im_end|>
  <|im_start|>user
  I need help with my order<|im_end|>
  <|im_start|>assistant
  I'd be happy to help. Could you provide your order number?<|im_end|>
  <|im_start|>user
  It's ORDER-123<|im_end|>
  <|im_start|>assistant
  ```
  - or converted into this prompt (for Llama 3.2 model):
  ```
  <|begin_of_text|><|start_header_id|>system<|end_header_id|>

  Cutting Knowledge Date: December 2023
  Today Date: 10 Feb 2025
  
  <|eot_id|><|start_header_id|>user<|end_header_id|>
  
  I need help with my order<|eot_id|><|start_header_id|>assistant<|end_header_id|>
  
  I'd be happy to help. Could you provide your order number?<|eot_id|><|start_header_id|>user<|end_header_id|>
  
  It's ORDER-123<|eot_id|><|start_header_id|>assistant<|end_header_id|>
  ```
  
- ChatML is a template format that structures conversations with clear role indicators
- When using an instruct model we need to use the correct chat template for the model (different models need different templates)
- Example chat template (for SmolLM2-135M-Instruct):
```
{% for message in messages %}
{% if loop.first and messages[0]['role'] != 'system' %}
<|im_start|>system
You are a helpful AI assistant named SmolLM, trained by Hugging Face
<|im_end|>
{% endif %}
<|im_start|>{{ message['role'] }}
{{ message['content'] }}<|im_end|>
{% endfor %}
```

- Messages to prompt:
```python
from transformers import AutoTokenizer

tokenizer = AutoTokenizer.from_pretrained("HuggingFaceTB/SmolLM2-1.7B-Instruct")
rendered_prompt = tokenizer.apply_chat_template(messages, tokenize=False, add_generation_prompt=True)
```

- [More information about chat templates](https://huggingface.co/docs/transformers/main/en/chat_templating)
- Create a chat template with [Jinja](https://jinja.palletsprojects.com/en/stable/templates/)
- Also see [Template writing guide](https://huggingface.co/docs/transformers/main/en/chat_templating_writing)

- Multimodal templates: can handle images, videos and texts 

### What is an AI Tool?

- A tool is a function given to the LLM
- Examples: Web search, image generation, API interface
- A tool should contain:
  - A textural description of what the tool does
  - A Callable (something to perform the action)
  - Arguments with typings
  - (optional) Outputs with typings
- How to give tools to the LLM:
  - In the system message
- Create a python function and decorate it with `@tool`
- Example:
```python
@tool
def calculator(a: int, b: int) -> int:
    """Multiply two integers."""
    return a * b

print(calculator.to_string())
```
- `to_string()` prints `Tool Name: calculator, Description: Multiply two integers., Arguments: a: int, b: int, Outputs: int`
- Hint: create [a generatic tool class](https://huggingface.co/learn/agents-course/unit1/tools#generic-tool-implementation)
- Model Context Protocol (MCP): a unified tool interface
  - Is an open source protocol that standardizes how tools are defined and used in LLMs

### Agent Workflow

- Thought-Action-Observation Cycle:
  - Thought: LLM decides what steps to take
  - Action: Calling a tool with associated arguments
  - Observation: LLM reflects on the response of the tool
  - is a while-loop until the task is completed by the agent
- Rules and guidelines can be embedded directly into system message
```
system_message="""You are an AI assistant designed to help users effectively and accuratly. Your primary goal is to provide helpful, precise and clear responses.

You have access to the following tools:
Tool Name: calculator, Description: Multiply two integers., Arguments: a: int, b: int, Outputs, int

You should think step by step in order to fulfill the objective with a reasoning devided into 
Thought/Action/Observation steps that ca be repeated multiple times if needed.

You should first reflect on the current situation usint 'Thought: {your_thoughts}', 
then (if necessary) call a tool with the proper JSON formatting 'Action: {JSON_BLOB}' 
or print your final answer starting with the prefix 'Final answer:'
"""
```
- Interplay of Though-Action-Observation cycle empowers AI agents to perform complex tasks iteratively

- **ReAct approach**:
  - Reasoning (Thought) + Acting (Action) 
  - "Let's think step by step": prompting the model to think step by step
    - Encourages the model to decompose the problem into sub-tasks 

- Types of agent actions:
  - JSON Agent: Action is specified in JSON format
  - Code Agent: Agent writes a code block that is interpreted externally
  - Function-calling Agent: subcategory of JSON Agent. Generate a new message for each action.
- **Stop and Parse approach**:
  - The Agent outputs the action in a predetermined format (JSON, code)
  - LLM stops generating next tokens when the action is defined
  - External parser reads the formatted action, determines the tool to call, and executes it
```
Thought: I need to check the current weather for New York.
Action :
{
  "action": "get_weather",
  "action_input": {"location": "New York"}
}
```
- Code Agents must take care of [security](https://huggingface.co/docs/smolagents/tutorials/secure_code_execution)

- Observation phase:
  - Collects feedback
  - Appends results
  - Adapts its strategy 

[Dummy agent](notebooks/Agent-course-dummyAgent.ipynb)

### Building an agent with smolagent

- [smolagents blog post](https://huggingface.co/blog/smolagents)
- [smolagents GitHub Repo](https://github.com/huggingface/smolagents)
- focus on code Agents

Next: https://huggingface.co/learn/agents-course/bonus-unit1/introduction