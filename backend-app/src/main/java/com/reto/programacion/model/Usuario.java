package com.reto.programacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_nombre")
    private String nombre;

    @Column(name = "user_apellido")
    private String apellido;

    @Column(name = "user_genero")
    private String genero;

    @Column(name = "user_nacionalidad")
    private String nacionalidad;

    @Column(name = "user_telefono")
    private String telefono;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_contrasena", length = 60) // Longitud para BCrypt
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasena;

    @Column(name = "refresh_token_hash", length = 60)
    @JsonIgnore
    private String refreshTokenHash;

    @Column(name = "refresh_token_expiry")
    private Instant refreshTokenExpiry;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Post> posts;


    // Métodos para refresh token
    public void generarNuevoRefreshToken() {
        String tokenPlano = UUID.randomUUID().toString();
        this.refreshTokenHash = BCrypt.hashpw(tokenPlano, BCrypt.gensalt());
        this.refreshTokenExpiry = Instant.now().plus(7, ChronoUnit.DAYS);
    }

    public boolean validarRefreshToken(String tokenPlano) {
        return BCrypt.checkpw(tokenPlano, this.refreshTokenHash) &&
                this.refreshTokenExpiry.isAfter(Instant.now());
    }

    public void invalidarRefreshToken() {
        this.refreshTokenHash = null;
        this.refreshTokenExpiry = null;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}
