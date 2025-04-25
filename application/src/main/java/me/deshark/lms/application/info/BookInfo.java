package me.deshark.lms.application.info;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @author DE_SHARK
 */
@Data
@Builder
public class BookInfo {
    private String isbn;
    private String isbn10;
    private String title;
    private String author;
    private String publisher;
    private short publishedYear;
    private String language;
    private String pageCount;
    private String coverImageUrl;
    private String description;
    private OffsetDateTime createdAt;
    private int currentCopyCount;
    private int availableCopyCount;
}
