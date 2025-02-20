package me.deshark.lms.infrastructure.config;

import me.deshark.lms.domain.repository.auth.UserRepository;
import me.deshark.lms.domain.service.auth.AuthService;
import me.deshark.lms.domain.service.auth.PasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DE_SHARK
 * @date 2025/2/14 16:07
 */
@Configuration
public class DomainConfig {

    @Bean
    public AuthService authService(UserRepository userRepository, PasswordEncryptor passwordEncryptor) {
        return new AuthService(userRepository, passwordEncryptor);
    }
}
