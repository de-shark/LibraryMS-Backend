package me.deshark.lms.domain.service.auth;

import me.deshark.lms.domain.model.auth.vo.AuthTokenPair;

import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/13 18:01
 */
public interface TokenProvider {

    // 生成两个令牌
    AuthTokenPair generateToken(String username, UUID userId, String role);

    // 生成访问令牌
    String generateAccessToken(String username, UUID userId, String role);

    // 生成刷新令牌
    String generateRefreshToken(String username, UUID userId, String role);

    // 验证 Token 有效性
    boolean validateToken(String refreshToken);

    // 从refresh token中解析用户名和角色
    String getUsernameFromToken(String refreshToken);

    String getRoleFromToken(String refreshToken);
}
