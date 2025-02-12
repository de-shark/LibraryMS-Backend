package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.domain.model.entity.AuthUser;
import me.deshark.lms.domain.model.vo.Password;
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
                .password(authUser.getPassword().encryptedPassword())
                .status(authUser.getStatus())
                .build();
    }

    public AuthUser toDomainEntity(UserDO userDO) {
        return AuthUser.builder()
                .username(userDO.getUsername())
                .email(userDO.getEmail())
                .password(new Password(userDO.getPassword()))
                .status(userDO.getStatus())
                .build();
    }
}
