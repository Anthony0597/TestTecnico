package com.reto.programacion.service.auth;

import com.reto.programacion.dto.AuthResponse;
import com.reto.programacion.model.Usuario;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    public ResponseEntity singUp(Usuario usuario);
    public ResponseEntity login(String email, String pass);
    public ResponseEntity<?> refreshToken(String refreshToken);
}
