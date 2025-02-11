package me.deshark.lms.infrastructure.repository;

import me.deshark.lms.infrastructure.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author DE_SHARK
 */
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Integer> {

    boolean existsByUsername(String username);

    UserJpaEntity findByUsername(String username);
}
