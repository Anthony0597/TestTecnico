package com.reto.programacion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer id;
    @Column(name = "post_titulo")
    private String titulo;
    @Column(name = "post_contenido")
    private String contenido;
    @Column(name = "post_fecha_publicacion")
    private LocalDateTime fechaPublicacion;
    @Column(name = "privado")
    private Boolean privado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;
}
