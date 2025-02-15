package me.deshark.lms.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.deshark.lms.domain.model.auth.entity.AuthUser;
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
@Component
public class JwtTokenProvider implements TokenProvider {

    private final SecretKey key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        // 使用 Keys 工具类来生成适当长度的密钥
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(AuthUser authUser) {
        Instant now = Instant.now();
        
        return Jwts.builder()
                .subject(authUser.getUsername())
                // 设置签发时间
                .issuedAt(Date.from(now))
                // 设置过期时间（例如24小时后）
                .expiration(Date.from(now.plus(24, ChronoUnit.HOURS)))
                // 使用新的签名方法
                .signWith(key)
                .compact();
    }
}
