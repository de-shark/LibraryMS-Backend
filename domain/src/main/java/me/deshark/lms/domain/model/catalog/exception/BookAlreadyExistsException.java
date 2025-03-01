package me.deshark.lms.domain.model.catalog.exception;

/**
 * @author DE_SHARK
 */
public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
