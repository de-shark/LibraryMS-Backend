package me.deshark.lms.application.info.userprofile;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Getter
@Builder
public class UserProfileInfo {
    UUID userId;
    String username;
    String email;
}
