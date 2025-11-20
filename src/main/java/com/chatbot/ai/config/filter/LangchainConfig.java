package com.chatbot.ai.config.filter;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangchainConfig {

    @Bean
    public ChatLanguageModel chatModel() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:0.5b")
                .build();
    }
}
