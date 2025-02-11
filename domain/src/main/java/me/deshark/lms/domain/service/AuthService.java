package me.deshark.lms.domain.service;

import me.deshark.lms.common.exception.AuthenticationException;
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

    public AuthUser authenticate(String username, String rawPassword) {
        AuthUser user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().matches(rawPassword)) {
            throw new AuthenticationException("用户名或密码错误");
        }
        return user;
    }
}
