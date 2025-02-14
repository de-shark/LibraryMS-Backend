package me.deshark.lms.application.service;

import me.deshark.lms.application.info.UserInfo;
import me.deshark.lms.common.exception.AuthenticationException;
import me.deshark.lms.common.exception.UsernameAlreadyExistedException;
import me.deshark.lms.common.utils.Result;
import me.deshark.lms.domain.model.entity.AuthUser;
import me.deshark.lms.domain.repository.UserRepository;
import me.deshark.lms.domain.service.AuthService;
import me.deshark.lms.domain.service.TokenProvider;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
//@RequiredArgsConstructor
public class AuthAppService {

    // 领域层接口
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthService authService;

    // 手动添加构造函数，Spring 会自动注入 userRepository
    public AuthAppService(UserRepository userRepository, TokenProvider tokenProvider, AuthService authService) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authService = authService;
    }

    public Result<String, String> register(UserInfo userInfo) {
        final AuthUser newUser;
        // 1. 请求领域实体
        try {
            newUser = authService.registerUser(userInfo.username(), userInfo.password(), userInfo.email());
        } catch (UsernameAlreadyExistedException e) {
            return Result.err(e.getMessage());
        }

        // 2. 保存新用户到数据库
        boolean success = userRepository.save(newUser);
        return success ? Result.ok("注册成功")
                : Result.err("系统错误，请联系管理员");
    }

    public Result<String, String> login(UserInfo userInfo) {
        try {
            AuthUser authUser = authService.authenticate(userInfo.username(), userInfo.password());
            // 生成 Token
            String token = tokenProvider.generateToken(authUser);
            return Result.ok(token);
        } catch (AuthenticationException e) {
            return Result.err(e.getMessage());
        }
    }
}
