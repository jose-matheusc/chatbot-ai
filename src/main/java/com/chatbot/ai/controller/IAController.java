package com.chatbot.ai.controller;

import com.chatbot.ai.service.IAService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/ia")
public class IAController {

    private final IAService iaService;

    public IAController(IAService iaService) {
        this.iaService = iaService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String q) {
        return iaService.ask(q);
    }

    @GetMapping(path ="/perguntar", version = "1.2")
    public String perguntar2(@RequestParam String q) {
        return iaService.perguntar(q);
    }

}
