package me.deshark.lms.application.service;

import me.deshark.lms.application.info.AuthToken;
import me.deshark.lms.application.info.UserInfo;
import me.deshark.lms.common.exception.UsernameAlreadyExistedException;
import me.deshark.lms.common.utils.Result;
import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.model.auth.vo.AuthTokenPair;
import me.deshark.lms.domain.service.auth.AuthService;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
public class AuthAppService {

    private final AuthService authService;

    public AuthAppService(AuthService authService) {
        this.authService = authService;
    }

    public Result<String, String> register(UserInfo userInfo) {
        final AuthUser newUser;
        try {
            newUser = authService.registerUser(userInfo.username(), userInfo.password(), userInfo.email());
            return Result.ok("注册成功");
        } catch (UsernameAlreadyExistedException e) {
            return Result.err(e.getMessage());
        } catch (Exception e) {
            return Result.err("系统错误，请联系管理员");
        }
    }

    public AuthToken login(String username, String password) {
        AuthTokenPair authTokenPair = authService.authenticate(username, password);
        return AuthToken.builder()
                .accessToken(authTokenPair.getAccessToken())
                .refreshToken(authTokenPair.getRefreshToken())
                .build();
    }

    public AuthToken refresh(String refreshToken) {
        AuthTokenPair authTokenPair = authService.refreshToken(refreshToken);
        return AuthToken.builder()
                .accessToken(authTokenPair.getAccessToken())
                .refreshToken(authTokenPair.getRefreshToken())
                .build();
    }
}
