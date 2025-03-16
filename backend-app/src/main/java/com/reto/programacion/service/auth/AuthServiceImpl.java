package com.reto.programacion.service.auth;

import com.reto.programacion.dto.AuthResponse;
import com.reto.programacion.model.Role;
import com.reto.programacion.model.Usuario;
import com.reto.programacion.repository.IUsuarioRepository;
import com.reto.programacion.security.JwtUtil;
import com.reto.programacion.security.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity singUp(Usuario usuario) {
        try {
            if(validarSingUp(usuario)){
                if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent() ) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario con este email ya existe");
                }else{
                    // Hashear contraseña
                    usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena())); // Usa el setter que aplica BCrypt
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
    public ResponseEntity login(String email, String pass) {
        try{

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, pass)
            );
            if(auth.isAuthenticated()) {
                Usuario usuario = userDetailsService.getUsuarioDetail();

                // Generar tokens
                String accessToken = jwtUtil.generateToken(usuario.getEmail(), usuario.getRole().name());
                usuario.generarNuevoRefreshToken();
                usuarioRepository.save(usuario);

                // Crear respuesta
                AuthResponse response = new AuthResponse(
                        accessToken,
                        usuario.getRefreshTokenHash(), // En producción usa el token plano, no el hash
                        usuario.getRefreshTokenExpiry()
                );

                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
        }
    }


    @Override
    public ResponseEntity refreshToken(String refreshToken) {
        try {
            Usuario usuario = usuarioRepository.findByRefreshTokenHash(refreshToken)
                    .orElseThrow(() -> new RuntimeException("Token inválido"));

            if(usuario.validarRefreshToken(refreshToken)) {
                String newAccessToken = jwtUtil.generateToken(usuario.getEmail(), usuario.getRole().name());
                usuario.generarNuevoRefreshToken();
                usuarioRepository.save(usuario);

                return ResponseEntity.ok(new AuthResponse(
                        newAccessToken,
                        usuario.getRefreshTokenHash(),
                        usuario.getRefreshTokenExpiry()
                ));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }

    private boolean validarSingUp(Usuario usuario) {
        return usuario.getNombre() != null &&
                usuario.getApellido() != null &&
                usuario.getEmail() != null &&
                usuario.getContrasena() != null;
    }

    // Métodos para gestión de contraseña
    public String encodeContrasena(String contrasenaPlana) {
        return BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());
    }

    public boolean verificarContrasena(String email, String contrasenaPlana) {
        Usuario user = usuarioRepository.findByEmail(email).get();
        return BCrypt.checkpw(contrasenaPlana, user.getContrasena());
    }
}
