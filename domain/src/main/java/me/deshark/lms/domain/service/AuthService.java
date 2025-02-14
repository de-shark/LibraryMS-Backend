package me.deshark.lms.domain.service;

import me.deshark.lms.common.exception.UsernameAlreadyExistedException;
import me.deshark.lms.domain.model.entity.AuthUser;
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

        // 2. 注册新用户
        AuthUser newUser = AuthUser.createUser(username, password, email);
        userRepository.save(newUser);
        return newUser;
    }

    public AuthUser authenticate(String username, String rawPassword) {
        AuthUser user = userRepository.findByUsername(username);
        user.authenticate(rawPassword);
        return user;
    }
}
