package me.deshark.lms.interfaces.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DE_SHARK
 */
@Getter
@AllArgsConstructor
public class UserInfoResponse {
    String username;
    String email;
}
