package com.reto.programacion.dto;

import lombok.Data;

@Data
public class PostDTO {
    private int id;
    private String titulo;

    public PostDTO() {}
    public PostDTO(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }
}
