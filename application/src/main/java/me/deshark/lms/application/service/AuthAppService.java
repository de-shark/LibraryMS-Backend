package me.deshark.lms.application.service;

import me.deshark.lms.application.dto.LoginRequest;
import me.deshark.lms.application.dto.LoginResponse;
import me.deshark.lms.application.dto.RegisterRequest;
import me.deshark.lms.application.dto.RegisterResponse;
import me.deshark.lms.common.exception.AuthenticationException;
import me.deshark.lms.common.exception.UsernameAlreadyExistedException;
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

    public RegisterResponse register(RegisterRequest request) {
        // 1. 校验用户名唯一性（领域规则）
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistedException("用户名已存在");
        }

        // 2. 将 DTO 转换为领域对象
        AuthUser authUser = AuthUser.builder()
                .username(request.username())
                .password(Password.encrypt(request.password()))
                .email(request.email())
                .role(UserRole.READER)
                .status(UserStatus.UNVERIFIED)
                .build();

        // 3. 保存到数据库（通过 Repository）
        try {
            int result = userRepository.save(authUser);
            if (result == 0) {
                return new RegisterResponse(false, "注册失败");
            } else if (result == 1) {
                return new RegisterResponse(true, "注册成功");
            } else {
                return new RegisterResponse(false, "出现插入行数大于1的异常情况");
            }
        } catch (UsernameAlreadyExistedException e) {
            return new RegisterResponse(false, e.getMessage());
        }
    }

    public LoginResponse login(LoginRequest request) {
        try {
            AuthUser authUser = authService.authenticate(request.username(), request.password());
            // 生成 Token
            String token = tokenProvider.generateToken(authUser);
            return new LoginResponse(authUser.getUsername(), token);
        } catch (AuthenticationException e) {
            return new LoginResponse(null, null);
        }
    }
}
