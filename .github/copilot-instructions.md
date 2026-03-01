- Don't use comments in code
- Don't use `;;;;` section-separator comments
- Use concise docstrings to explain the purpose of functions
- Don't put docstrings on defs
- Favour readability over cleverness in code
- Write short functions with a single responsibility
- Make use of defn- for functions not referenced outside of the namespace, but don't use private flags on defs
- Use British english spelling (e.g -ise instead of -ize where appropriate and colour instead of color)
- Use destructuring in function signatures where it aids readability (e.g. `[{:keys [pipeline-name model]}]`)
- Prefer threading macros (`->`, `->>`) for data transformation pipelines
- Keep the README up to date with instructions for running and testing the code, and any other relevant information for developers working on the project. Add mermaid diagrams for workflows where appropriate.

## Project context

- The purpose of this project is to track the value of investments over time by calling an external API to retrieve the current and historic value of investments
- Along with the data from the external API we need the user to provide us with transaction data (e.g. buy/sell history) to calculate the value of investments over time
- Eventually I think I'll want a front end for this project, where users can submit data and see visualisations. But Initially I just want a backend with APIs providing this functionality.
- I want to containerise this project so I can run it in docker locally and eventually maybe in the cloud
- Currently the only free API I can find for retrieving these values is provided by the Financial Times, which is in the format https://markets.ft.com/data/equities/ajax/get-historical-prices?startDate={year}%2F{two digit month}%2F{two digit day}&endDate={year}%2F{two digit month}%2F{two digit day}&symbol={symbol, I'm not sure what this is but it's not the ISIN which is what I'd like to use} unfortunately it returns its data in an HTML table format. If you can find a better API I'm all ears.

## Architecture & state management

- Avoid global mutable state — only use atoms or delays as a last resort for configuration or credentials
- Use Integrant for component lifecycle management
- Use Aero for configuration with `#profile` tags for per-environment values
- Pass the whole config map through functions as an explicit argument rather than individual keys, except where a narrower interface genuinely makes sense

## Testing

- Use `clojure.test` with eftest as the test runner
- Add and update tests as part of every change unless told otherwise
- Favour quality of tests over quantity — a few well-chosen tests over exhaustive coverage
- Use table-driven tests (doseq/are over test cases) for readability and extensibility

## Workflow

- For non-trivial work, create a markdown plan file first, then work through it step by step
- Use the plan to track progress and ensure nothing is missed
- Run the tests as you progress through work without input from me
- Review what you have done in full before handing back to me
- If you have non-trivial questions, ask me for clarification in the form of options rather than making assumptions

## Libraries & conventions

- This is a deps.edn project
- Use clj-http (synchronous) for HTTP calls, with `:as :json` and `:throw-exceptions false`
- Use cheshire for JSON encoding
- Use taoensso.timbre for logging
- When selecting new libraries, search the Clojure ecosystem for the most recent and well-maintained options, and prefer those that are widely used in the community
- When importing new libraries search maven or clojars to ensure they are the most recent releases
