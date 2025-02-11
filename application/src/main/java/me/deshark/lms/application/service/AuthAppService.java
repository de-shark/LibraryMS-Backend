package me.deshark.lms.application.service;

import me.deshark.lms.application.dto.RegisterRequest;
import me.deshark.lms.application.dto.RegisterResponse;
import me.deshark.lms.common.exception.UsernameAlreadyExistedException;
import me.deshark.lms.domain.model.entity.AuthUser;
import me.deshark.lms.domain.model.vo.Password;
import me.deshark.lms.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
//@RequiredArgsConstructor
public class AuthAppService {

    // 领域层接口
    private final UserRepository userRepository;

    // 手动添加构造函数，Spring 会自动注入 userRepository
    public AuthAppService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegisterResponse register(RegisterRequest request) {
        // 1. 校验用户名唯一性（领域规则）
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistedException("用户名已存在");
        }

        // 2. 将 DTO 转换为领域对象（可能涉及密码加密）
        AuthUser user = AuthUser.create(
                request.email(),
                request.username(),
                // 密码加密（值对象）
                Password.encrypt(request.password())
        );

        // 3. 保存到数据库（通过 Repository）
        AuthUser savedUser =  userRepository.save(user);
        if (savedUser == null) {
            return new RegisterResponse(true);
        } else {
            return new RegisterResponse(false);
        }
    }
}
