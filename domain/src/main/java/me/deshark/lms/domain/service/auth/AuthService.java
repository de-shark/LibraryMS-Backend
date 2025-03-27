package me.deshark.lms.domain.service.auth;

import me.deshark.lms.common.exception.auth.AuthenticationException;
import me.deshark.lms.common.exception.auth.UsernameAlreadyExistedException;
import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.model.auth.vo.AuthTokenPair;
import me.deshark.lms.domain.model.auth.vo.TokenRequest;
import me.deshark.lms.domain.repository.auth.UserRepository;

/**
 * @author DE_SHARK
 */
public class AuthService {

    private static final String ERROR_USER_NOT_EXIST = "用户不存在";
    private static final String ERROR_INVALID_REFRESH_TOKEN = "无效的刷新令牌";
    private static final String ERROR_USER_DISABLED = "用户已被禁用";

    private final UserRepository userRepository;
    private final PasswordEncryptor encryptor;
    private final TokenProvider tokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncryptor encryptor, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.encryptor = encryptor;
        this.tokenProvider = tokenProvider;
    }

    public void registerUser(String username, String email, String password) {

        // 1. 校验用户名唯一性
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistedException(username);
        }

        // 2. 注册新用户
        AuthUser newUser = AuthUser.createUser(username, password, email, encryptor);
        // 激活用户（没有邮箱验证前的替代方法）
        newUser.activate();
        userRepository.save(newUser);
    }

    public AuthTokenPair authenticate(String username, String rawPassword) {
        AuthUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException(ERROR_USER_NOT_EXIST));
        user.authenticate(rawPassword, encryptor);
        return generateToken(new TokenRequest(username, user.getUuid(), user.getUserRole().name()));
    }

    public AuthTokenPair generateToken(TokenRequest tokenRequest) {
        return tokenProvider.generateToken(tokenRequest.getUsername(), tokenRequest.getUserId(), tokenRequest.getRole());
    }

    public AuthTokenPair refreshToken(String refreshToken) {
        // 1. 验证refresh token有效性
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new AuthenticationException(ERROR_INVALID_REFRESH_TOKEN);
        }
        
        // 2. 从refresh token中解析用户名
        String username = tokenProvider.getUsernameFromToken(refreshToken);
        
        // 3. 查找用户并验证状态
        return userRepository.findByUsername(username)
                .filter(AuthUser::isActive)
                .map(user -> tokenProvider.generateToken(username, user.getUuid(), user.getUserRole().name()))
                .orElseThrow(() -> {
                    AuthUser user = userRepository.findByUsername(username)
                            .orElseThrow(() -> new AuthenticationException(ERROR_USER_NOT_EXIST));
                    
                    // 用户存在但不活跃
                    return new AuthenticationException(ERROR_USER_DISABLED);
                });
    }
}
