package me.deshark.lms.infrastructure.repository.userprofile;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.userprofile.UserProfile;
import me.deshark.lms.domain.repository.userprofile.UserProfileRepository;
import me.deshark.lms.infrastructure.mapper.profile.UserProfileMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class UserProfileReadRepositoryImpl implements UserProfileRepository {

    private final UserProfileMapper mapper;

    @Override
    public Optional<UserProfile> findByUserId(UUID userId) {
        return mapper.selectProfileView(userId);
    }
}
