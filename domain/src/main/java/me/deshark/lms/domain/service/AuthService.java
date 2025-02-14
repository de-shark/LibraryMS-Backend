package me.deshark.lms.domain.service;

import me.deshark.lms.common.exception.AuthenticationException;
import me.deshark.lms.common.exception.UsernameAlreadyExistedException;
import me.deshark.lms.domain.model.entity.AuthUser;
import me.deshark.lms.domain.model.vo.Password;
import me.deshark.lms.domain.model.vo.UserRole;
import me.deshark.lms.domain.model.vo.UserStatus;
import me.deshark.lms.domain.repository.UserRepository;

/**
 * @author DE_SHARK
 */
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthUser registerUser(String username, String password, String email) {

        // 1. 校验用户名唯一性
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistedException(username);
        }

        // 2. 创建领域实体
        return AuthUser.builder()
                .username(username)
                .password(Password.encrypt(password))
                .email(email)
                .role(UserRole.READER)
                .status(UserStatus.UNVERIFIED)
                .build();
    }

    public AuthUser authenticate(String username, String rawPassword) {
        AuthUser user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().matches(rawPassword)) {
            throw new AuthenticationException("用户名或密码错误");
        }
        return user;
    }
}
