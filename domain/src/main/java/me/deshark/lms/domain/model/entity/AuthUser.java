package me.deshark.lms.domain.model.entity;

import me.deshark.lms.common.exception.AuthenticationException;
import me.deshark.lms.domain.model.vo.Password;
import me.deshark.lms.domain.model.vo.UserRole;
import me.deshark.lms.domain.model.vo.UserStatus;

/**
 * @author DE_SHARK
 */
public class AuthUser {
    private String email;
    private String username;
    private Password password;
    private UserRole role;
    private UserStatus status;

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
        private String email;
        private String username;
        private Password password;
        private UserRole role;
        private UserStatus status;

        public Builder email(String email) {
            this.email = email;
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
    public static AuthUser createUser(String username, String rawPassword, String email) {
        return AuthUser.builder()
                .email(email)
                .username(username)
                .password(Password.encrypt(rawPassword))
                .role(UserRole.READER)
                .status(UserStatus.UNVERIFIED)
                .build();
    }

    // 业务行为方法
    public void authenticate(String rawPassword) {
        if (!password.matches(rawPassword)) {
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
