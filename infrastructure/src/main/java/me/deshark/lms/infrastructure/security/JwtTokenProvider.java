package me.deshark.lms.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.deshark.lms.domain.model.entity.AuthUser;
import me.deshark.lms.domain.service.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author DE_SHARK
 * @date 2025/2/13 14:30
 */
@Component
public class JwtTokenProvider implements TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public String generateToken(AuthUser authUser) {
        // 使用JWT库生成Token
        return Jwts.builder()
                .setSubject(authUser.getUsername())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
