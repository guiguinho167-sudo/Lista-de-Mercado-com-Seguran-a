package com.lista.lista;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtToken {
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    public String gerartoken(String gmail) {
        Date agora = new Date();
        Date expirar = new Date(agora.getTime() + 1000 * 60 * 50);
        return Jwts.builder()
                .subject(gmail)
                .issuedAt(agora)
                .expiration(expirar)
                .signWith(key)
                .compact();
    }

    public String validartoken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }

}
