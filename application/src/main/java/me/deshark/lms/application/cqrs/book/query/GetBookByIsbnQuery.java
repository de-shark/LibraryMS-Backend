package me.deshark.lms.application.cqrs.book.query;

/**
 * @author DE_SHARK
 */
public record GetBookByIsbnQuery(
        String isbn
) {}