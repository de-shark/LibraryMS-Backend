package me.deshark.lms.domain.model.catalog.exception;

/**
 * @author deshark
 */
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
    
    public BookNotFoundException(String isbn, Throwable cause) {
        super("无法找到ISBN: " + isbn, cause);
    }
}
