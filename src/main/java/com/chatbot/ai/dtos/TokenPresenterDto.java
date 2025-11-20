package com.chatbot.ai.dtos;


import java.util.List;

public class TokenPresenterDto {

    private String username;
    private List<String> roles;

    public TokenPresenterDto() {}

    public TokenPresenterDto(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}

