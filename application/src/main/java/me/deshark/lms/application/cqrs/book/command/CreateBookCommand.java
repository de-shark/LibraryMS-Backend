package me.deshark.lms.application.cqrs.book.command;

/**
 * @author DE_SHARK
 */
public record CreateBookCommand(
        String isbn
) {}
