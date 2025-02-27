package me.deshark.lms.application.cqrs.book.query;

/**
 * @author DE_SHARK
 */
public record SearchBooksQuery(
        String keyword,
        int page,
        int size
) {}
