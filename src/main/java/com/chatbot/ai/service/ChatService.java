package com.chatbot.ai.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatLanguageModel model;
    private final ChromaEmbeddingStore embeddingStore;

    public ChatService(@Qualifier("chatModel") ChatLanguageModel model) {
        this.model = model;
        ChromaEmbeddingStore store = null;
        try {
            store = ChromaEmbeddingStore.builder()
                .baseUrl("http://localhost:8000")
                .collectionName("documentos")
                .build();
        } catch (RuntimeException e) {
            System.err.println("Erro ao conectar ou criar coleção no Chroma: " + e.getMessage());
        }
        this.embeddingStore = store;
    }

    public String addDocument(String text) {
        return "Documento armazenado no vetor store! (implemente geração de embedding)";
    }

    // ---------- PERGUNTAS (RAG) ---------------------
    public String ask(String question) {
        String context = "(implemente busca de contexto relevante)";
        String prompt = """
                Você é um assistente que usa o contexto abaixo:

                CONTEXTO:
                %s

                Pergunta: %s
                Resposta:
                """.formatted(context, question);
        return model.generate(prompt);
    }
}
