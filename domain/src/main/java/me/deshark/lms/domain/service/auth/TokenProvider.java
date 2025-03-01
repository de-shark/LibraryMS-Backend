package me.deshark.lms.domain.service.auth;

import me.deshark.lms.domain.model.auth.vo.AuthTokenPair;

/**
 * @author DE_SHARK
 * @date 2025/2/13 18:01
 */
public interface TokenProvider {
    AuthTokenPair generateToken(String username, String role);

    // 生成访问令牌
    String generateAccessToken(String username, String role);

    // 生成刷新令牌
    String generateRefreshToken(String username);
}
