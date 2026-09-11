package ch.bbw.pr.tresorbackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JwtTokenProvider - Erstellt und validiert JWT Tokens
 * Access Token: 15 Minuten Gültigkeit
 * Refresh Token: 7 Tage Gültigkeit
 */
@Component
public class JwtTokenProvider {

    private final SecretKey key;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public String generateAccessToken(String email, String role) {
        return generateToken(email, role, accessTokenExpiration);
    }

    public String generateRefreshToken(String email, String role) {
        return generateToken(email, role, refreshTokenExpiration);
    }

    /**
     * Generiert einen temporären Token für den 2FA-Zwischenschritt.
     * Gültigkeit: 5 Minuten
     */
    public String generateTempToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim("type", "2fa-temp")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 300000)) // 5 min
                .signWith(key)
                .compact();
    }

    private String generateToken(String email, String role, long expiration) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }

    public String getTokenType(String token) {
        return getClaims(token).get("type", String.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
