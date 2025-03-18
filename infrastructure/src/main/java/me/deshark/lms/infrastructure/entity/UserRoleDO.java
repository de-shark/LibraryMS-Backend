package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;
import me.deshark.lms.infrastructure.enums.UserRoleType;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Data
@Builder
public class UserRoleDO {
    private final UUID userRoleId;
    private UUID userId;
    private UserRoleType role;
    private Timestamp createdAt;
}
