package com.justicia.usuario_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "ProyectoAudiencias12345678901234567890";

    private static final long EXPIRATION_TIME = 8 * 60 * 60 * 1000;

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generarToken(String usuarioId, String rol) {
        return Jwts.builder()
                .setSubject(usuarioId)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public String obtenerUsuarioId(String token) {
        return validarToken(token).getBody().getSubject();
    }

    public String obtenerRol(String token) {
        return validarToken(token).getBody().get("rol", String.class);
    }
}
