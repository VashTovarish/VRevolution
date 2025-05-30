package com.example.backend.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String fio;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String fio, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.fio = fio;
        this.email = email;
        this.roles = roles;
    }
}

