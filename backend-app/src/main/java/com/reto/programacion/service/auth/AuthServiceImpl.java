package com.reto.programacion.service.auth;

import com.reto.programacion.model.Role;
import com.reto.programacion.model.Usuario;
import com.reto.programacion.repository.IUsuarioRepository;
import com.reto.programacion.security.JwtUtil;
import com.reto.programacion.security.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public ResponseEntity<String> singUp(Usuario usuario) {
        try {
            if(validarSingUp(usuario)){
                if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent() ) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario con este email ya existe");
                }else{
                    usuario.setRole(Role.USER);
                    usuarioRepository.save(usuario);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Registro exitoso");
                }
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campos vacios");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Algo salio mal");
    }

    @Override
    public ResponseEntity<String> login(String email, String pass) {
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, pass)
            );

            System.out.println("Auth: "+auth.getName()+" "+auth.isAuthenticated());

            if(auth.isAuthenticated()) {
                return new ResponseEntity<String>("{\"token\":\""+jwtUtil.generateToken(userDetailsService.getUsuarioDetail().getEmail(),
                        String.valueOf(userDetailsService.getUsuarioDetail().getRole()))
                        +"\"}", HttpStatus.OK);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Credenciales inválidas\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"algo salio mal\"}");
    }

    private boolean validarSingUp(Usuario usuario) {
        if(usuario.getNombre()!=null && usuario.getApellido()!=null && usuario.getEmail()!=null) {
            return true;
        }
        return false;
    }
}
