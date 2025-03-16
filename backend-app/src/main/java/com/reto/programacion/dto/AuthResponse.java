package com.reto.programacion.dto;

import java.time.Instant;

public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Instant refreshTokenExpiry;

    // Constructor
    public AuthResponse(String accessToken, String refreshToken, Instant refreshTokenExpiry) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    // Getters y Setters (necesarios para la serialización JSON)
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public Instant getRefreshTokenExpiry() { return refreshTokenExpiry; }
    public void setRefreshTokenExpiry(Instant refreshTokenExpiry) { this.refreshTokenExpiry = refreshTokenExpiry; }
}
