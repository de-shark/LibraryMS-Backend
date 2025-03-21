package me.deshark.lms.common.exception.auth;

/**
 * @author DE_SHARK
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
