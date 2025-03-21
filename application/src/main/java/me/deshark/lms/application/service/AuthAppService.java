package me.deshark.lms.application.service;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.info.AuthToken;
import me.deshark.lms.domain.model.auth.vo.AuthTokenPair;
import me.deshark.lms.domain.service.auth.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DE_SHARK
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthAppService {

    private static final Logger logger = LoggerFactory.getLogger(AuthAppService.class);

    private final AuthService authService;

    private AuthToken convertToAuthToken(AuthTokenPair tokenPair) {
        return AuthToken.builder()
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void register(String username, String email, String rawPassword) {
        logger.info("新用户注册: {}, {}", username, email);
        authService.registerUser(username, email, rawPassword);
        logger.info("用户注册成功: {}", username);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public AuthToken login(String username, String password) {
        logger.info("用户登录尝试: {}", username);
        AuthTokenPair authTokenPair = authService.authenticate(username, password);
        logger.info("用户登录成功: {}", username);
        return convertToAuthToken(authTokenPair);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public AuthToken refresh(String refreshToken) {
        logger.debug("尝试刷新令牌");
        AuthTokenPair authTokenPair = authService.refreshToken(refreshToken);
        logger.debug("令牌刷新成功");
        return convertToAuthToken(authTokenPair);
    }
}
