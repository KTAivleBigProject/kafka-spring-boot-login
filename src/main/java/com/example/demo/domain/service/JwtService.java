package com.example.demo.domain.service;

import com.example.demo.domain.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "my-secret-key-for-jwt-very-secret-and-long";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // ✅ Access Token 생성 (subject에 email 저장)
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())  // ✅ email이 username
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ email 추출
    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    // ✅ 유효성 검사
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ✅ JWT 클레임 추출
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ 헤더에서 Bearer 토큰 추출
    public String resolveToken(javax.servlet.http.HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}