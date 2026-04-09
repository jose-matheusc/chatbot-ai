package com.chatbot.ai.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatLanguageModel model;
    private final ChromaEmbeddingStore embeddingStore;

    public ChatService(@Qualifier("ollamaModel") ChatLanguageModel model) {
        this.model = model;
        ChromaEmbeddingStore store = null;
        try {
            store = ChromaEmbeddingStore.builder()
                .baseUrl("http://localhost:8000")
                .collectionName("documentos")
                .build();
        } catch (RuntimeException ignored) {
        }
        this.embeddingStore = store;
    }

    public String addDocument() {
        return "Document stored in the vector store! (implement embedding generation)";
    }

    // ---------- QUESTIONS (RAG) ---------------------
    public String ask(String question) {
        String context = "(implement search for relevant context)";
        String prompt = """
                You are an assistant that uses the context below:

                CONTEXT:
                %s

                Question: %s
                Answer:
                """.formatted(context, question);
        return model.generate(prompt);
    }
}
