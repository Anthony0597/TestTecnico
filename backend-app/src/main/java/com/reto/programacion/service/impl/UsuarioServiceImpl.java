package com.reto.programacion.service.impl;

import com.reto.programacion.dto.UsuarioDTO;
import com.reto.programacion.model.Usuario;
import com.reto.programacion.repository.IUsuarioRepository;
import com.reto.programacion.security.JwtUtil;
import com.reto.programacion.security.UsuarioDetailsService;
import com.reto.programacion.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).get();
    }

    @Override
    public List<UsuarioDTO> findAllDTO() {
        return usuarioRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    public UsuarioDTO findByIdDTO(Integer id) {
        return convertToDTO(usuarioRepository.findById(id).get());
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        System.out.println("Hola");
        // Obtén la entidad gestionada desde la base de datos
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        System.out.println(usuarioExistente.getNombre());

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setRole(usuario.getRole());
        usuarioExistente.setNacionalidad(usuario.getNacionalidad());
        usuarioExistente.setGenero(usuario.getGenero());
        String aux =passwordEncoder.encode(usuario.getContrasena());
        System.out.println(aux);
        usuarioExistente.setContrasena(aux);

        System.out.println(usuarioExistente.getContrasena());

        // Guarda la entidad gestionada con los cambios
        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario findByEmail(String email) {
        if(usuarioRepository.findByEmail(email).isPresent()) {
            return usuarioRepository.findByEmail(email).get();
        }
        return null;
    }


    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setRole(usuario.getRole());
        return usuarioDTO;
    }


}
