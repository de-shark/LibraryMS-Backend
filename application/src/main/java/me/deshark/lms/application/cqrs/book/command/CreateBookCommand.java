package me.deshark.lms.application.cqrs.book.command;

import lombok.Data;

/**
 * @author DE_SHARK
 */
@Data
public class CreateBookCommand {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private short publishedYear;
    private String description;
}
