package me.deshark.lms.domain.model.auth.vo;

/**
 * @author DE_SHARK
 */
public class TokenRequest {
    private final String username;
    private final String role;

    public TokenRequest(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
