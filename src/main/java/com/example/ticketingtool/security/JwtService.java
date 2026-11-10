
package com.example.ticketingtool.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:1800000}")
    private long expiration;

    // Generate signing key from Base64 secret
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate JWT token
    public String generateToken(String email) {

        Date currentDate = new Date();

        Date expirationDate =
                new Date(currentDate.getTime() + expiration);

        return Jwts.builder()
                .subject(email)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    // Extract email from JWT token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract all claims after verifying signature
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Check whether token is expired
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // Validate token against email
    public boolean isTokenValid(String token, String email) {

        try {
            String tokenEmail = extractUsername(token);

            return tokenEmail.equals(email)
                    && !isTokenExpired(token);

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
