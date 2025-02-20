package me.deshark.lms.infrastructure.security;

import me.deshark.lms.domain.service.auth.PasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * @author DE_SHARK
 * @date 2025/2/20 16:25
 */
@Component
public class BcryptPasswordEncryptor implements PasswordEncryptor {
    @Override
    public String encrypt(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}
