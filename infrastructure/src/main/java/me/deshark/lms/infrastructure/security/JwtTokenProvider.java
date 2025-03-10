package me.deshark.lms.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import me.deshark.lms.domain.model.auth.vo.AuthTokenPair;
import me.deshark.lms.domain.service.auth.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * JWT Token 提供者实现
 * @author DE_SHARK
 */
@Getter
@Component
public class JwtTokenProvider implements TokenProvider {

    private final SecretKey key;
    private final int ACCESS_TOKEN_EXPIRATION_TIME = 15 * 60;
    private final int REFRESH_TOKEN_EXPIRATION_TIME = 14 * 24 * 60 * 60;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        // 使用 Keys 工具类来生成适当长度的密钥
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public AuthTokenPair generateToken(String username, String role) {
        return new AuthTokenPair(
                generateAccessToken(username, role),
                generateRefreshToken(username, role)
        );
    }

    // 生成访问令牌
    @Override
    public String generateAccessToken(String username, String role) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(ACCESS_TOKEN_EXPIRATION_TIME, ChronoUnit.SECONDS)))
                .signWith(key)
                .compact();
    }

    // 生成刷新令牌
    @Override
    public String generateRefreshToken(String username, String role) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(REFRESH_TOKEN_EXPIRATION_TIME, ChronoUnit.SECONDS)))
                .signWith(key)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public String getRoleFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}
