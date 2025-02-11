package me.deshark.lms.common.exception;

/**
 * @author DE_SHARK
 */
public class UsernameAlreadyExistedException extends RuntimeException {
    public UsernameAlreadyExistedException(String username) {
        super("Username " + username + " already existed");
    }
}
