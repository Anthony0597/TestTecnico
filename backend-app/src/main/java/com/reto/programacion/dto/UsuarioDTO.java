package com.reto.programacion.dto;

import com.reto.programacion.model.Role;
import lombok.Data;

@Data
public class UsuarioDTO {
    private int id;
    private String nombre;
    private String apellido;
    private Role role;

    public UsuarioDTO() {}
    public UsuarioDTO(int id, String nombre, String apellido, Role role) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.role = role;
    }
}
