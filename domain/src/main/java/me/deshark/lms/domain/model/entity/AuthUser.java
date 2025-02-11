package me.deshark.lms.domain.model.entity;

import me.deshark.lms.domain.model.vo.Password;

/**
 * @author DE_SHARK
 */
public class AuthUser {
    private String email;
    private String username;
    private Password password;
    private String status;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public String getStatus() {
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
        private String status;

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

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public AuthUser build() {
            AuthUser user = new AuthUser();
            user.email = this.email;
            user.username = this.username;
            user.password = this.password;
            user.status = this.status;
            return user;
        }
    }

    // 静态工厂方法：创建用户
    public static AuthUser create(String email, String username, Password password) {
        AuthUser authUser = new AuthUser();
        authUser.email = email;
        authUser.username = username;
        authUser.password = password;
        return authUser;
    }
}
