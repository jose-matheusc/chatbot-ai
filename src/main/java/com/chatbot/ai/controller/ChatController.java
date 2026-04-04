package com.chatbot.ai.controller;

import com.chatbot.ai.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping("/train")
    public String train(@RequestBody String text) {
        return service.addDocument(text);
    }

    @GetMapping
    public String ask(@RequestParam String q) {
        return service.ask(q);
    }
}
