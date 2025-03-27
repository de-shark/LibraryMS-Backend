package me.deshark.lms.domain.repository.userprofile;

import me.deshark.lms.domain.model.userprofile.UserProfile;

import java.util.Optional;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
public interface UserProfileRepository {
    Optional<UserProfile> findByUserId(UUID userId);
}
