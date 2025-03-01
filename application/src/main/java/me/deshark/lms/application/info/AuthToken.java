package me.deshark.lms.application.info;

import lombok.Builder;
import lombok.Getter;

/**
 * @author DE_SHARK
 */
@Getter
@Builder
public class AuthToken {
    private final String accessToken;
    private final String refreshToken;
}
