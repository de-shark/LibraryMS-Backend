package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.entity.AuthUser;
import me.deshark.lms.domain.repository.UserRepository;
import me.deshark.lms.infrastructure.entity.UserJpaEntity;
import me.deshark.lms.infrastructure.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Repository;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserPersistenceMapper mapper;

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public AuthUser save(AuthUser authUser) {
        UserJpaEntity entity = mapper.toJpaEntity(authUser);
        UserJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomainEntity(savedEntity);
    }

    @Override
    public AuthUser findByUsername(String username) {
        UserJpaEntity entity = jpaRepository.findByUsername(username);
        return mapper.toDomainEntity(entity);
    }
}
