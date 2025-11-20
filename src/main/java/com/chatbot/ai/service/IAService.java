package com.chatbot.ai.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.stereotype.Service;

@Service
public class IAService {

    private final ChatLanguageModel model;

    public IAService(ChatLanguageModel model) {
        this.model = model;
    }

    public String perguntar(String texto) {
        return model.generate(texto);
    }
}
