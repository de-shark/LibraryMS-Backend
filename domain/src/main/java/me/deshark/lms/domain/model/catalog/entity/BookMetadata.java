package me.deshark.lms.domain.model.catalog.entity;

import lombok.Builder;
import lombok.Getter;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

/**
 * @author DE_SHARK
 * @date 2025/2/15 20:47
 */
@Getter
@Builder
public class BookMetadata {
    private final Isbn isbn;
    private String title;
    private String author;
    private String publisher;
    private int publishedYear;
    private String description;
    private String coverUrl;

    public void updateDetails(String title, String author, String publisher, int publishedYear, String description) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.description = description;
    }
}
