package me.deshark.lms.application.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

/**
 * @author DE_SHARK
 */
@Getter
@Builder
public class AuthToken {
    private final String accessToken;
    @JsonIgnore
    private final String refreshToken;
}
