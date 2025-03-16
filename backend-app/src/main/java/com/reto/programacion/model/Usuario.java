package com.reto.programacion.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
    @Column(name = "user_contrasena")
    private String contrasena;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Post> posts;

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }


}
