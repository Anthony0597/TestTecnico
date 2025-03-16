package com.reto.programacion.controller.auth;

import com.reto.programacion.model.Usuario;
import com.reto.programacion.service.auth.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    IAuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        try {
            return authService.login(email, password);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Algo salio mal");
    }

    @PostMapping(path = "/singup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUsuario(@RequestBody Usuario usuario) {
        return authService.singUp(usuario);
    }

}
