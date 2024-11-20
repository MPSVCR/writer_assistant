# AI Hackathon MPSV 2024 – Assistant for Document Writing

The application is a technology demo developed during the AI Hackathon organized by MPSV in November 2024.

Its purpose is to test two approaches to AI support for writing official documents, which are often highly variable (e.g., records from local investigations).

The first approach allows users to summarize key points of a record in bullet points, from which the AI generates the text.
The second approach works as an autocomplete feature – offering three possible continuations of the entered text.

The application's UI is in Czech.

## Prerequisites
* backend: Java SDK 21 or higher
* frontend: NodeJS 22.11.0 or higher (with `npm` or `pnpm`)
* access to Azure OpenAI API (endpoint and API key)

## Installation

### Frontend
```shell
cd frontend
npm install
# or
pnpm install
```

## Build
When building, the frontend part needs to be compiled first, and it is then included in the backend build process.

### Frontend

```shell
cd frontend
npm build
# or
pnpm build
```

### Backend

```shell
cd backend
./gradlew build
```

## Development

### Backend
Create configuration file `backend/config/application.yaml`. You can use `backend/config/application.sample.yaml` as a template.

```shell
./gradlew bootRun
```

### Frontend
```shell
pnpm dev 
```
