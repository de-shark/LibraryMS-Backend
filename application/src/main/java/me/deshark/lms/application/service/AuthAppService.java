package me.deshark.lms.application.service;

import me.deshark.lms.application.info.UserInfo;
import me.deshark.lms.common.exception.AuthenticationException;
import me.deshark.lms.common.exception.UsernameAlreadyExistedException;
import me.deshark.lms.common.utils.Result;
import me.deshark.lms.domain.model.entity.AuthUser;
import me.deshark.lms.domain.model.vo.Password;
import me.deshark.lms.domain.model.vo.UserRole;
import me.deshark.lms.domain.model.vo.UserStatus;
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
    public AuthAppService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authService = new AuthService(userRepository);
    }

    public Result<String, String> register(UserInfo userInfo) {
        // 1. 校验用户名唯一性（领域规则）
        if (userRepository.existsByUsername(userInfo.username())) {
            throw new UsernameAlreadyExistedException("用户名已存在");
        }

        // 2. 将 DTO 转换为领域对象
        AuthUser authUser = AuthUser.builder()
                .username(userInfo.username())
                .password(Password.encrypt(userInfo.password()))
                .email(userInfo.email())
                .role(UserRole.READER)
                .status(UserStatus.UNVERIFIED)
                .build();

        // 3. 保存到数据库（通过 Repository）
        try {
            int result = userRepository.save(authUser);
            if (result == 1) {
                return Result.ok("注册成功");
            } else {
                return Result.err("注册失败，请询问管理员");
            }
        } catch (UsernameAlreadyExistedException e) {
            return Result.err(e.getMessage());
        }
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
