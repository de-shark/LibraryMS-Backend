package me.deshark.lms.infrastructure.repository;

import com.github.f4b6a3.uuid.alt.GUID;
import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.repository.auth.UserRepository;
import me.deshark.lms.infrastructure.entity.UserDO;
import me.deshark.lms.infrastructure.mapper.UserMapper;
import me.deshark.lms.infrastructure.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Repository;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;
    private final UserPersistenceMapper persistenceMapper;

    @Override
    public boolean existsByUsername(String username) {
        return userMapper.existsByUsername(username);
    }

    @Override
    public boolean save(AuthUser authUser) {
        UserDO userDO = persistenceMapper.toDataObject(authUser);
        // 生成 UUID v7
        userDO.setUuid(GUID.v7().toUUID());
        return userMapper.insert(userDO) == 1;
    }

    @Override
    public AuthUser findByUsername(String username) {
        UserDO userDO = userMapper.findByUsername(username);
        return userDO != null ? persistenceMapper.toDomainEntity(userDO) : null;
    }
}
