package me.deshark.lms.domain.model.auth.vo;

import lombok.Getter;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Getter
public class TokenRequest {
    private final String username;
    private final UUID userId;
    private final String role;

    public TokenRequest(String username, UUID userId, String role) {
        this.username = username;
        this.userId = userId;
        this.role = role;
    }
}
