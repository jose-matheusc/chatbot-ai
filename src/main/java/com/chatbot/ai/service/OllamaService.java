//package com.chatbot.ai.service;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OllamaService {
//
//    private final ChatClient chatClient;
//
//    @Autowired
//    public OllamaService(ChatClient.Builder builder) {
//        this.chatClient = builder.build();
//    }
//
//    public String chat(String question) {
//        return chatClient
//                .prompt()
//                .user(question)
//                .call()
//                .content();
//    }
//}

