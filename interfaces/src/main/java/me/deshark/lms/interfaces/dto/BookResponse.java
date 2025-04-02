package me.deshark.lms.interfaces.dto;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @author DE_SHARK
 */

@Data
public class BookResponse {
    private String isbn;
    private String isbn10;
    private String title;
    private String author;
    private String publisher;
    private short publishedYear;
    private String language;
    private String pageCount;
    private String coverImage;
    private String description;
    private OffsetDateTime createdAt;
    private int availableCopies;
}
