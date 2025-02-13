package me.deshark.lms.application.info;

import lombok.Builder;
import me.deshark.lms.domain.model.vo.UserRole;
import me.deshark.lms.domain.model.vo.UserStatus;

/**
 * @author DE_SHARK
 * @date 2025/2/13 22:33
 */
@Builder
public record UserInfo(
    String email,
    String username,
    String password,
    UserRole role,
    UserStatus status
) {
}
