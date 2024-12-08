package fr.makizart.restserver.services;

import fr.makizart.common.database.table.Utilisateur;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@Transactional
public class JwtService { // Extract the username from the token, and generate a token
    private final String secretKey = System.getenv("JWT_SECRET");

    public String generateToken(Utilisateur user) {
        Date now = new Date(); // Right now

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + (3 * 24 * 60 * 60 * 1000))) // 3 days
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token); // Use the helper function to extract the username
        return claims.getSubject(); // Username
    }

    //_--------------------------------------------------------------------------------
    // Helper functions
    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token or expired");
        }
    }

    private Key getSecretKey() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new RuntimeException("JWT secret key is not set in the environment");
        }
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
