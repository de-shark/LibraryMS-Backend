package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.repository.auth.UserRepository;
import me.deshark.lms.infrastructure.entity.UserDO;
import me.deshark.lms.infrastructure.entity.UserRoleDO;
import me.deshark.lms.infrastructure.mapper.UserMapper;
import me.deshark.lms.infrastructure.mapper.UserPersistenceMapper;
import me.deshark.lms.infrastructure.mapper.UserRoleMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserPersistenceMapper persistenceMapper;

    @Override
    public boolean existsByUsername(String username) {
        return userMapper.existsByUsername(username);
    }

    @Override
    public void save(AuthUser authUser) {
        UserDO userDO = persistenceMapper.toDataObject(authUser);
        if (userMapper.insert(userDO) != 1) {
            throw new IllegalArgumentException("User not saved");
        }
        ;
    }

    @Override
    public AuthUser findByUsername(String username) {
        Optional<UserDO> userDO = userMapper.findByUsername(username);
        if (userDO.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        Optional<UserRoleDO> userRoleDO = userRoleMapper.findByUserId(userDO.get().getUuid());

        AuthUser authUser = persistenceMapper.toDomainEntity(userDO.get());
        authUser.setUserRoleType(userRoleDO.map(UserRoleDO::getRole).orElse(null));
        return authUser;
    }
}
