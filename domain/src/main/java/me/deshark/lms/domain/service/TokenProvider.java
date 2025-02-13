package me.deshark.lms.domain.service;

import me.deshark.lms.domain.model.entity.AuthUser;

/**
 * @author DE_SHARK
 * @date 2025/2/13 18:01
 */
public interface TokenProvider {
    String generateToken(AuthUser authUser);
}
