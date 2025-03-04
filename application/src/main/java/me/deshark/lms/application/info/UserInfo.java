package me.deshark.lms.application.info;

import lombok.Builder;
import me.deshark.lms.domain.model.auth.vo.UserRoleType;
import me.deshark.lms.domain.model.auth.vo.UserStatus;

import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/13 22:33
 */
@Builder
public record UserInfo(
        UUID uuid,
        String username,
        String password,
        String email,
        UserRoleType role,
        UserStatus status
) {
}
