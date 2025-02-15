package me.deshark.lms.domain.model.auth.vo;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author DE_SHARK
 */
// 密码值对象（领域模型）
public record Password(String encryptedPassword) {

    // 加密逻辑（静态工厂方法）
    public static Password encrypt(String rawPassword) {
        String encrypted = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        return new Password(encrypted);
    }

    // 校验密码是否匹配
    public boolean matches(String rawPassword) {
        return BCrypt.checkpw(rawPassword, this.encryptedPassword);
    }
}
