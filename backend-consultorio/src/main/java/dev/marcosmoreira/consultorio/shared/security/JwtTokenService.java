package dev.marcosmoreira.consultorio.shared.security;

import dev.marcosmoreira.consultorio.auth.domain.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private final String jwtSecret;
    private final long accessTokenExpirationSeconds;

    public JwtTokenService(
            @Value("${app.security.jwt.secret:${security.jwt.secret:}}") String jwtSecret,
            @Value("${app.security.jwt.expiration-seconds:${security.jwt.access-token-expiration-seconds:3600}}") long accessTokenExpirationSeconds
    ) {
        this.jwtSecret = jwtSecret;
        this.accessTokenExpirationSeconds = accessTokenExpirationSeconds;
    }

    public String generateAccessToken(AuthUser authUser) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(accessTokenExpirationSeconds);

        return Jwts.builder()
                .subject(authUser.getUsername())
                .claim("usuarioId", authUser.getUsuarioId())
                .claim("nombreCompleto", authUser.getNombreCompleto())
                .claim("rolCodigo", authUser.getRolCodigo())
                .claim("rolNombre", authUser.getRolNombre())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username != null && username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationSeconds;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration == null || expiration.before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith((javax.crypto.SecretKey) getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
