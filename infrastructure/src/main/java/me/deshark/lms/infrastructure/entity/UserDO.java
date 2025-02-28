package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;
import me.deshark.lms.domain.model.auth.vo.UserRole;
import me.deshark.lms.domain.model.auth.vo.UserStatus;

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
    private UserRole role;
    private UserStatus status;
    private int creditScore;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
