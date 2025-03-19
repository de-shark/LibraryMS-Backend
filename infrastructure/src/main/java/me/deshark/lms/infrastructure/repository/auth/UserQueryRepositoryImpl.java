package me.deshark.lms.infrastructure.repository.auth;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.common.exception.auth.UsernameNotFoundException;
import me.deshark.lms.domain.repository.auth.UserQueryRepository;
import me.deshark.lms.infrastructure.entity.UserDO;
import me.deshark.lms.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final UserMapper userMapper;

    @Override
    public UUID getUserIdByUsername(String username) {
        UserDO userDO = userMapper.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
        return userDO.getUuid();
    }
}
