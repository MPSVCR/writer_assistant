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
./gradlew run
```
Open http://localhost:8080 in browser.

### Frontend
```shell
pnpm dev 
```

Open http://localhost:5173 in browser.

## Usage

At the beginning, you need to choose whether you want to use text creation from an outline (*Chat*), a suggestion tool (*Našeptávač*),
or text creation from an outline using a trained (fine-tuned) model (*Trénovaný chat*).

## Chat

Enter key information in bullet points (in Czech) that you want to include in the final text. For example:

> žije sám, pečuje o dvě nezletilé děti, matka dětí nezvěstná, občas vypomáhá matka, práci shání neúspěšně, žije z podpory

After creating the text, you can add more information to the input, based on which the text will be adjusted (re-generated).

AI is instructed to create a document with a predefined structure.

## Suggestion Tool (našeptávač)

Start typing (Czech) text. After pressing <kbd>Ctrl+Enter</kbd>, three possible continuations of the text will be displayed. Select one of them and press <kbd>Enter</kbd>.  
The text completion is offered at the position where the text cursor is placed.

For example:

> Pan Myslík žije sám, pečuje o dvě nezletilé děti. Matka dětí

## Trained Chat (Trénovaný chat)

Enter key information in bullet points (in Czech) that you want to include in the final text, similar to the basic text. For example:

> žije sám, pečuje o dvě nezletilé děti, matka dětí nezvěstná, občas vypomáhá matka, práci shání neúspěšně, žije z podpory

AI is instructed to create a document with a free structure, based solely on trained data.
