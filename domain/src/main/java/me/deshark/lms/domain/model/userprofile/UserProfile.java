package me.deshark.lms.domain.model.userprofile;

import lombok.Value;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Value
public class UserProfile {
    UUID userId;
    String username;
    String email;
}
