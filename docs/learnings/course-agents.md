# AI Agents

Source: [Hugging Face Agents Course](https://huggingface.co/learn/agents-course/unit0/introduction)

## Unit 0

Why do I need [Ollama](https://github.com/ollama/ollama/blob/main/README.md#quickstart)?

[Install Ollama](https://huggingface.co/learn/agents-course/unit0/onboarding#step-5-running-models-locally-with-ollama-in-case-you-run-into-credit-limits)

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

Next: https://huggingface.co/learn/agents-course/unit1/what-are-llms