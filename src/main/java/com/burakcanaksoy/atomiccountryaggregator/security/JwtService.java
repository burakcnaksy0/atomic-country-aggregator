package com.burakcanaksoy.atomiccountryaggregator.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @PostConstruct
    public void validateSecretKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            if (keyBytes.length < 32) {
                throw new IllegalArgumentException(
                        "JWT secret key must be at least 256 bits (32 bytes)"
                );
            }
            log.info("JWT secret key validated successfully");
        } catch (IllegalArgumentException e) {
            log.error("Invalid JWT secret key: {}", e.getMessage());
            throw e;
        }
    }

    // Token'dan username çıkarır.
    public String extractUsername(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        return extractClaim(token, Claims::getSubject);
    }

    // Token'dan belirli bir claim'i çıkarır
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Kullanıcı için JWT access token oluşturur
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


    // Ekstra claim'lerle JWT access token oluşturur
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    // Token oluşturma ana metodu
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        if (userDetails == null) {
            throw new IllegalArgumentException("UserDetails cannot be null");
        }

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())  // ✅ Algorithm otomatik seçilir
                .compact();
    }

    // Token'ın geçerli olup olmadığını kontrol eder
    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (token == null || userDetails == null) {
            log.warn("Token or UserDetails is null");
            return false;
        }

        try {
            final String username = extractUsername(token);
            boolean isValid = username != null
                    && username.equals(userDetails.getUsername())
                    && !isTokenExpired(token);

            if (!isValid) {
                log.warn("Token validation failed for user: {}", username);
            }

            return isValid;
        } catch (Exception e) {
            log.error("Token validation error: {}", e.getMessage());
            return false;
        }
    }

    // Token'ın süresinin dolup dolmadığını kontrol eder
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token'dan expiration tarihini çıkarır
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Token'dan tüm claim'leri çıkarır
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new RuntimeException("Token has expired", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token format: {}", e.getMessage());
            throw new RuntimeException("Invalid token format", e);
        } catch (SignatureException e) {
            log.error("JWT signature validation failed: {}", e.getMessage());
            throw new RuntimeException("Invalid token signature", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
            throw new RuntimeException("Unsupported token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new RuntimeException("Token is empty", e);
        }
    }

    // Secret key'i Key nesnesine çevirir
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}