package me.deshark.lms.application.service;

import me.deshark.lms.application.info.AuthToken;
import me.deshark.lms.domain.model.auth.vo.AuthTokenPair;
import me.deshark.lms.domain.service.auth.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DE_SHARK
 */
@Service
public class AuthAppService {

    private final AuthService authService;

    public AuthAppService(AuthService authService) {
        this.authService = authService;
    }

    @Transactional
    public void register(String username, String email, String rawPassword) {
        authService.registerUser(
                username,
                email,
                rawPassword
        );
    }

    @Transactional
    public AuthToken login(String username, String password) {
        AuthTokenPair authTokenPair = authService.authenticate(username, password);
        return AuthToken.builder()
                .accessToken(authTokenPair.getAccessToken())
                .refreshToken(authTokenPair.getRefreshToken())
                .build();
    }

    @Transactional
    public AuthToken refresh(String refreshToken) {
        AuthTokenPair authTokenPair = authService.refreshToken(refreshToken);
        return AuthToken.builder()
                .accessToken(authTokenPair.getAccessToken())
                .refreshToken(authTokenPair.getRefreshToken())
                .build();
    }
}
