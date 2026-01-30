package com.example.music.JwtUtility;

import com.example.music.Domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
    public class JwtUtil {

        private final String SECRET = "segredo_super_secreto";

        public String generateToken(User user) {
            return Jwts.builder()
                    .setSubject(user.getEmail())
                    .claim("id", user.getId())
                    .claim("premium", user.isPremium())
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();
        }

        public Claims extractClaims(String token) {
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        }

        public boolean isTokenValid(String token, UserDetails userDetails) {
            String email = extractClaims(token).getSubject();
            return email.equals(userDetails.getUsername());
        }
    }
