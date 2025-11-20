package com.chatbot.ai.controller.jwt;

import com.chatbot.ai.dtos.TokenPresenterDto;
import com.chatbot.ai.service.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @PostMapping("/jwt/created")
    public ResponseEntity<String> createdTokenJwt(@RequestBody TokenPresenterDto tokenPresenterDto){
        return ResponseEntity.ok(jwtService.generateToken(tokenPresenterDto));
    }
}
