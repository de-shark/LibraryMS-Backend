package me.deshark.lms.common.exception.auth;

/**
 * @author DE_SHARK
 */
public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
