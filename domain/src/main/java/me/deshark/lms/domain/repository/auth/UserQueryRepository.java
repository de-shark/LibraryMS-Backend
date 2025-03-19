package me.deshark.lms.domain.repository.auth;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
public interface UserQueryRepository {
    UUID getUserIdByUsername(String username);
}
