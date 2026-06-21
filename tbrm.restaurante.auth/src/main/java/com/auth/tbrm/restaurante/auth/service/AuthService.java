package com.auth.tbrm.restaurante.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION;

    private SecretKey getSigningKey() {
        logger.info("Obteniendo llave de firma para JWT");

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateToken(UserDetails usuario) {
        logger.info("Generando token JWT para username={}", usuario.getUsername());

        String token = Jwts.builder()
                .subject(usuario.getUsername())
                .claim("roles", usuario.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey())
                .compact();

        logger.info("Token JWT generado correctamente para username={}", usuario.getUsername());

        return token;
    }

    public String extractUsername(String token) {
        logger.info("Extrayendo username desde token JWT");

        String username = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        logger.info("Username extraído correctamente desde token JWT. username={}", username);

        return username;
    }

    public boolean isValidToken(String token, UserDetails user) {
        logger.info("Validando token JWT para username={}", user.getUsername());

        String username = extractUsername(token);

        boolean valid = username.equals(user.getUsername()) && !isExpiredToken(token);

        if (valid) {
            logger.info("Token JWT válido para username={}", user.getUsername());
        } else {
            logger.warn("Token JWT inválido para username={}", user.getUsername());
        }

        return valid;
    }

    private boolean isExpiredToken(String token) {
        logger.info("Verificando expiración de token JWT");

        boolean expired = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());

        if (expired) {
            logger.warn("Token JWT expirado");
        } else {
            logger.info("Token JWT no expirado");
        }

        return expired;
    }
}