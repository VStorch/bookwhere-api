package com.viniciusstorch.bookwhere_api.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.viniciusstorch.bookwhere_api.account.model.Account;
import com.viniciusstorch.bookwhere_api.security.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
    
    private final JwtProperties jwtProperties;
    private Key signingKey;

    public String generateToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getEmail())
                .claim("id", account.getId())
                .claim("role", account.getRole().name())
                .claim("name", account.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMs()))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    @PostConstruct
    private void initSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getSignKey() {
        return signingKey;
    }
}
