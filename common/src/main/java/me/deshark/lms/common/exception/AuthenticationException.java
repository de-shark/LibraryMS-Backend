package me.deshark.lms.common.exception;

/**
 * @author DE_SHARK
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
