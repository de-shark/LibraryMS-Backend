package me.deshark.lms.infrastructure.config;

import me.deshark.lms.domain.repository.UserRepository;
import me.deshark.lms.domain.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DE_SHARK
 * @date 2025/2/14 16:07
 */
@Configuration
public class DomainConfig {

    @Bean
    public AuthService authService(UserRepository userRepository) {
        return new AuthService(userRepository);
    }
}
