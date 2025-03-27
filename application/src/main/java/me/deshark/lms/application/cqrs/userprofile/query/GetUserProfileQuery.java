package me.deshark.lms.application.cqrs.userprofile.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.deshark.lms.application.cqrs.core.Query;
import me.deshark.lms.application.info.userprofile.UserProfileInfo;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Getter
@AllArgsConstructor
public class GetUserProfileQuery implements Query<UserProfileInfo> {
    private final UUID userId;
}
