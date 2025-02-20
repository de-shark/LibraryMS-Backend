package me.deshark.lms.domain.service.auth;

/**
 * @author DE_SHARK
 * @date 2025/2/20 16:24
 */
public interface PasswordEncryptor {
    String encrypt(String rawPassword);

    boolean matches(String rawPassword, String encryptedPassword);
}
