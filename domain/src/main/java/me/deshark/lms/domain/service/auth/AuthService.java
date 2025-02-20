package me.deshark.lms.domain.service.auth;

import me.deshark.lms.common.exception.UsernameAlreadyExistedException;
import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.repository.auth.UserRepository;

/**
 * @author DE_SHARK
 */
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncryptor encryptor;

    public AuthService(UserRepository userRepository, PasswordEncryptor encryptor) {
        this.userRepository = userRepository;
        this.encryptor = encryptor;
    }

    public AuthUser registerUser(String username, String password, String email) {

        // 1. 校验用户名唯一性
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistedException(username);
        }

        // 2. 注册新用户
        AuthUser newUser = AuthUser.createUser(username, password, email, encryptor);
        userRepository.save(newUser);
        return newUser;
    }

    public AuthUser authenticate(String username, String rawPassword) {
        AuthUser user = userRepository.findByUsername(username);
        user.authenticate(rawPassword, encryptor);
        return user;
    }
}
