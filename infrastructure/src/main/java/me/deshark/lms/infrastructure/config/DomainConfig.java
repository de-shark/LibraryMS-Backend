package me.deshark.lms.infrastructure.config;

import me.deshark.lms.domain.repository.catalog.BookCopyRepository;
import me.deshark.lms.domain.repository.borrow.BorrowRepository;
import me.deshark.lms.domain.repository.auth.UserRepository;
import me.deshark.lms.domain.service.borrow.BorrowService;
import me.deshark.lms.domain.service.auth.AuthService;
import me.deshark.lms.domain.service.auth.PasswordEncryptor;
import me.deshark.lms.domain.service.auth.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DE_SHARK
 * @date 2025/2/14 16:07
 */
@Configuration
public class DomainConfig {

    @Bean
    public AuthService authService(
            UserRepository userRepository,
            PasswordEncryptor passwordEncryptor,
            TokenProvider tokenProvider
    ) {
        return new AuthService(userRepository, passwordEncryptor, tokenProvider);
    }

    @Bean
    public BorrowService borrowService(
            BorrowRepository borrowRepository,
            BookCopyRepository bookCopyRepository
    ) {
        return new BorrowService(borrowRepository, bookCopyRepository);
    }
}
