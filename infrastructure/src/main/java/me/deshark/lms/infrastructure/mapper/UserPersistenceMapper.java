package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.model.auth.vo.Password;
import me.deshark.lms.infrastructure.entity.UserDO;
import org.springframework.stereotype.Component;

/**
 * @author DE_SHARK
 */
// 对象映射器
@Component
public class UserPersistenceMapper {

    public UserDO toDataObject(AuthUser authUser) {
        return UserDO.builder()
                .username(authUser.getUsername())
                .email(authUser.getEmail())
                .password(authUser.getPassword().encryptedValue())
                .role(authUser.getRole())
                .status(authUser.getStatus())
                .build();
    }

    public AuthUser toDomainEntity(UserDO userDO) {
        return AuthUser.builder()
                .username(userDO.getUsername())
                .email(userDO.getEmail())
                .password(new Password(userDO.getPassword()))
                .role(userDO.getRole())
                .status(userDO.getStatus())
                .build();
    }
}
