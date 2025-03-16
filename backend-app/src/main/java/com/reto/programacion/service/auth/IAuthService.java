package com.reto.programacion.service.auth;

import com.reto.programacion.model.Usuario;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    public ResponseEntity<String> singUp(Usuario usuario);
    public ResponseEntity<String> login(String email, String pass);
}
