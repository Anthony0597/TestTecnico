package com.reto.programacion.controller;

import com.reto.programacion.dto.UsuarioDTO;
import com.reto.programacion.model.Usuario;
import com.reto.programacion.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Integer id) {
        UsuarioDTO usuario = usuarioService.findByIdDTO(id);
        return ResponseEntity.status(200).body(usuario);
    }

    @GetMapping(path = "/comp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> getUsuarioByIdComp(@PathVariable Integer id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.status(200).body(usuario);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.findAllDTO();
        return ResponseEntity.status(200).body(usuarios);
    }

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUsuario(@RequestBody Usuario usuario) {
        usuarioService.save(usuario);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        usuarioService.save(usuario);
    }

    @DeleteMapping(path = "/{id}", consumes = MediaType.ALL_VALUE)
    public void deleteUsuario(@PathVariable Integer id) {
        usuarioService.delete(id);
    }

}
