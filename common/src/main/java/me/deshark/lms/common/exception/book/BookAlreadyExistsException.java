package me.deshark.lms.common.exception.book;

/**
 * @author DE_SHARK
 */
public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
