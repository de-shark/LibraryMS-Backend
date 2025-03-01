package me.deshark.lms.application.service;

import me.deshark.lms.application.info.UserInfo;
import me.deshark.lms.common.exception.AuthenticationException;
import me.deshark.lms.common.exception.UsernameAlreadyExistedException;
import me.deshark.lms.common.utils.Result;
import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.service.auth.AuthService;
import me.deshark.lms.domain.service.auth.TokenProvider;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
//@RequiredArgsConstructor
public class AuthAppService {

    // 领域层接口
    private final TokenProvider tokenProvider;
    private final AuthService authService;

    // 手动添加构造函数，Spring 会自动注入 userRepository
    public AuthAppService(TokenProvider tokenProvider, AuthService authService) {
        this.tokenProvider = tokenProvider;
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

    public Result<String, String> login(String username, String password) {
        try {
            AuthUser authUser = authService.authenticate(username, password);
            // 生成 Token
            String token = tokenProvider.generateToken(authUser);
            return Result.ok(token);
        } catch (AuthenticationException e) {
            return Result.err(e.getMessage());
        }
    }
}
