package me.deshark.lms.domain.model.auth.entity;

import me.deshark.lms.common.exception.AuthenticationException;
import me.deshark.lms.domain.model.auth.vo.Password;
import me.deshark.lms.domain.model.auth.vo.UserRole;
import me.deshark.lms.domain.model.auth.vo.UserStatus;
import me.deshark.lms.domain.service.auth.PasswordEncryptor;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
public class AuthUser {
    private UUID uuid;
    private String username;
    private Password password;
    private String email;
    private UserRole role;
    private UserStatus status;

    public UUID getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    // 添加Builder支持
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID uuid;
        private String username;
        private Password password;
        private String email;
        private UserRole role;
        private UserStatus status;

        public Builder uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder status(UserStatus status) {
            this.status = status;
            return this;
        }

        public AuthUser build() {
            AuthUser user = new AuthUser();
            user.email = this.email;
            user.username = this.username;
            user.password = this.password;
            user.role = this.role;
            user.status = this.status;
            return user;
        }
    }

    // 创建新用户
    public static AuthUser createUser(String username, String rawPassword, String email, PasswordEncryptor encryptor) {
        return AuthUser.builder()
                .email(email)
                .username(username)
                .password(Password.fromEncrypted(encryptor.encrypt(rawPassword)))
                .role(UserRole.READER)
                .status(UserStatus.UNVERIFIED)
                .build();
    }

    // 业务行为方法
    public void authenticate(String rawPassword, PasswordEncryptor encryptor) {
        if (!password.matches(rawPassword, encryptor)) {
            throw new AuthenticationException("用户名或密码错误");
        }
        if (status != UserStatus.ACTIVE) {
            throw new AuthenticationException("用户状态异常: " + status);
        }
    }

    // 状态变更方法
    public void activate() {
        this.status = UserStatus.ACTIVE;
    }
}
