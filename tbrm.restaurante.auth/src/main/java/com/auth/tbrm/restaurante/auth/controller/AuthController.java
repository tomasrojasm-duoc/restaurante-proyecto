package com.auth.tbrm.restaurante.auth.controller;

import com.auth.tbrm.restaurante.auth.dto.LoginRequestDto;
import com.auth.tbrm.restaurante.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authManager;
    private final AuthService service;
    private final UserDetailsService userDetailsService;

    public AuthController(
            AuthenticationManager authManager,
            AuthService service,
            UserDetailsService userDetailsService
    ) {
        this.authManager = authManager;
        this.service = service;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto req) {
        logger.info("Intento de login recibido. username={}", req.getUsername());

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(),
                            req.getPassword()
                    )
            );

            logger.info("Credenciales autenticadas correctamente. username={}", req.getUsername());

            UserDetails user = userDetailsService.loadUserByUsername(req.getUsername());
            String token = service.generateToken(user);

            logger.info("Token generado correctamente para username={}", req.getUsername());

            return ResponseEntity.ok(Map.of("token", token));

        } catch (BadCredentialsException e) {
            logger.warn("Intento de login fallido por credenciales incorrectas. username={}", req.getUsername());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }
    }
}