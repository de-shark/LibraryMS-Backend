package me.deshark.lms.common.exception.book;

import java.util.UUID;

/**
 * @author deshark
 */
public class BookCopyNotFoundException extends RuntimeException {
    public BookCopyNotFoundException(String message) {
        super(message);
    }
    
    public BookCopyNotFoundException(UUID bookCopyId) {
        super("无法找到图书副本: " + bookCopyId.toString());
    }
}
