package com.reto.programacion.controller.auth;

import com.reto.programacion.model.Usuario;
import com.reto.programacion.security.JwtUtil;
import com.reto.programacion.service.auth.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    IAuthService authService;
    @Autowired
    JwtUtil jwtUtil;

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

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshRequest refreshRequest) {
        try {
            if (refreshRequest.getRefreshToken() == null) {
                return ResponseEntity.badRequest().body(
                        Collections.singletonMap("error", "Refresh token requerido")
                );
            }

            var authResponse = authService.refreshToken(refreshRequest.getRefreshToken());

            return ResponseEntity.ok()
                    .header("Cache-Control", "no-store")
                    .body(authResponse);

        } catch (RuntimeException e) {
            String errorMessage = switch (e.getMessage()) {
                case "Token inválido" -> "Token de refresco no válido";
                case "Token expirado" -> "Token de refresco expirado";
                default -> "Error al procesar el token";
            };

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", errorMessage));
        }
    }

    // Clase DTO para la solicitud
    private static class RefreshRequest {
        private String refreshToken;

        // Getter y Setter necesarios para la deserialización
        public String getRefreshToken() { return refreshToken; }
        public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    }
}
