package com.shopflow.authservice.dto;
public class AuthResponseDTO {
    private String token;
    private Long userId;
    private String message;
    public AuthResponseDTO(String token, Long userId, String message) {
        this.token = token; this.userId = userId; this.message = message;
    }
    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public String getMessage() { return message; }
}
