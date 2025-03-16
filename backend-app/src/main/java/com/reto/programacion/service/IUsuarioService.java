package com.reto.programacion.service;

import com.reto.programacion.dto.UsuarioDTO;
import com.reto.programacion.model.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUsuarioService {
    public List<Usuario> findAll();
    public Usuario findById(Integer id);
    public List<UsuarioDTO> findAllDTO();
    public UsuarioDTO findByIdDTO(Integer id);
    public Usuario save(Usuario usuario);
    public Usuario update(Usuario usuario);
    public void delete(Integer id);
    public Usuario findByEmail(String email);

}
