package me.deshark.lms.domain.model.auth.vo;

import me.deshark.lms.domain.service.auth.PasswordEncryptor;

/**
 * @author DE_SHARK
 */
// 密码值对象（领域模型）
public record Password(String encryptedValue) {

    // 加密逻辑（静态工厂方法）
    public static Password fromEncrypted(String encryptedValue) {
        return new Password(encryptedValue);
    }

    // 校验密码是否匹配
    public boolean matches(String rawPassword, PasswordEncryptor encryptor) {
        return encryptor.matches(rawPassword, this.encryptedValue);
    }
}
