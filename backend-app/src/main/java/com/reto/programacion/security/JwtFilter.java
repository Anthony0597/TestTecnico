package com.reto.programacion.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioDetailsService userDetailsService;

    private String username =null;
    private Claims claims = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().matches("/usuarios/login|/usuarios/singup")){
            filterChain.doFilter(request, response);
        }else {
            String tokenHeader = request.getHeader("Authorization");
            String token = null;
            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                token = tokenHeader.substring(7);
                if (TokenBlackList.isBlacklisted(token)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                    return;
                }
                username = jwtUtil.extractUsername(token);
                claims = jwtUtil.extractAllClaims(token);
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    new WebAuthenticationDetailsSource().buildDetails(request);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                filterChain.doFilter(request, response);
                return;
            }
            filterChain.doFilter(request, response);
        }
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
