package com.reto.programacion.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioDetailsService userDetailsService;

    private String username =null;
    private Claims claims = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        // Permitir endpoints públicos sin procesar el token
        if (request.getServletPath().matches("/auth/login|/auth/singup|/posts/allpublic")) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        Claims claims = null;

        // Extraer y validar token
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);

            // Validar token no vacío
            if (token.isBlank()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token vacío");
                return;
            }

            // Verificar blacklist
            if (TokenBlackList.isBlacklisted(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return;
            }

            try {
                username = jwtUtil.extractUsername(token);
                claims = jwtUtil.extractAllClaims(token);
            } catch (Exception e) {
                logger.error("Error procesando token JWT: ", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return;
            }
        }

        // Cargar autenticación si el token es válido
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Extraer el rol del token, asumiendo que está en el claim "rol"
            String role = claims.get("rol", String.class);
            List<GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
            );

            // Validar token contra el userDetails si lo necesitas (opcional)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, // o puedes pasar username
                                null,
                                authorities // asignamos las autoridades extraídas del token
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    public Boolean isAdmin(){
        return "ADMIN".equals(claims.get("role"));
    }

    public Boolean isUsuario(){
        return "USER".equals(claims.get("role"));
    }

    public String getCurrentUser(){
        return username;
    }
}
