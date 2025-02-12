package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.domain.model.entity.AuthUser;
import me.deshark.lms.domain.model.vo.Password;
import me.deshark.lms.infrastructure.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

/**
 * @author DE_SHARK
 */
// 对象映射器
@Component
public class UserPersistenceMapper {

    public UserJpaEntity toJpaEntity(AuthUser authUser) {
        return UserJpaEntity.builder()
                .username(authUser.getUsername())
                .email(authUser.getEmail())
                .password(authUser.getPassword().encryptedPassword())
                .status(authUser.getStatus())
                .build();
    }

    public AuthUser toDomainEntity(UserJpaEntity userJpaEntity) {
        return AuthUser.builder()
                .username(userJpaEntity.getUsername())
                .email(userJpaEntity.getEmail())
                .password(new Password(userJpaEntity.getPassword()))
                .status(userJpaEntity.getStatus())
                .build();
    }
}
