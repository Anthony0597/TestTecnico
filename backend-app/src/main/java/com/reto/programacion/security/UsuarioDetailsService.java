package com.reto.programacion.security;

import com.reto.programacion.model.Usuario;
import com.reto.programacion.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    private Usuario usuarioDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        usuarioDetail = usuarioRepository.findByEmail(username).get();
        if (!Objects.isNull(usuarioDetail)) {
            return new org.springframework.security.core.userdetails.User(usuarioDetail.getEmail(), usuarioDetail.getContrasena(), new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    public Usuario getUsuarioDetail() {
        return usuarioDetail;
    }
}
