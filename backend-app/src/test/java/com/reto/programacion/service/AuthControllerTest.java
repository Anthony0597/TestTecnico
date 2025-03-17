package com.reto.programacion.service;

import com.reto.programacion.controller.auth.AuthController;
import com.reto.programacion.model.Usuario;
import com.reto.programacion.security.JwtUtil;
import com.reto.programacion.service.auth.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private IAuthService authService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        // Arrange
        Map<String, String> body = Map.of("email", "test@example.com", "password", "password");
        when(authService.login(anyString(), anyString())).thenReturn(ResponseEntity.ok("token"));

        // Act
        ResponseEntity<String> response = authController.login(body);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody());
    }

    @Test
    void testSingUp() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setContrasena("password");
        when(authService.singUp(any(Usuario.class))).thenReturn(ResponseEntity.ok("Usuario creado"));

        // Act
        ResponseEntity<String> response = authController.createUsuario(usuario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario creado", response.getBody());
    }


}