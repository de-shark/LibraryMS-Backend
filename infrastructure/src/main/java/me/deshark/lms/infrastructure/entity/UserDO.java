package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;
import me.deshark.lms.domain.model.vo.UserRole;
import me.deshark.lms.domain.model.vo.UserStatus;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Data
@Builder
public class UserDO {
    private UUID uuid;
    private String username;
    private String password;
    private String email;
    private Timestamp createdAt;
    private UserRole role;
    private UserStatus status;
}
