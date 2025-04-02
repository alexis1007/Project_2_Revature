package org.example.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.example.Service.JwtService;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    
    // URLs que no requieren autenticación
    private static final List<String> PUBLIC_URLS = Arrays.asList(
            "/api/auth/login",
            "/api/auth/register"
    );

    public JwtFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Skip filter for public URLs
        String path = request.getRequestURI();

        System.out.println("Request path: " + path);
        System.out.println("HTTP Method: " + request.getMethod());

        if (PUBLIC_URLS.stream().anyMatch(path::startsWith)) {
            System.out.println("Public URL, skipping filter.");
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            try {
                String username = jwtService.extractUsername(jwt);
                System.out.println("Username from JWT: " + username);
                Optional<User> user = userRepository.findByUsername(username);
                System.out.println("User present: " + user.isPresent());

                if (username != null && user.isPresent() && jwtService.validateToken(jwt, username)) {
                    request.setAttribute("user", user.get());
                    System.out.println("Token válido. Autenticación exitosa.");
                    filterChain.doFilter(request, response);
                    return;
                }else{System.out.println("Token inválido o usuario no encontrado.");}
            } catch (Exception e) {
                // Token invalid
                System.out.println("Error al validar el token:");
                e.printStackTrace();
            }
        }else{System.out.println("No se encontró el header Authorization o no es Bearer.");}

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized");
    }
}