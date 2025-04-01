package me.deshark.lms.domain.model.catalog.entity;

import lombok.Builder;
import lombok.Data;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

/**
 * @author DE_SHARK
 * @date 2025/2/15 20:47
 */
@Data
@Builder
public class BookMetadata {
    private final Isbn isbn;
    private String title;
    private String author;
    private String publisher;
    private short publishedYear;
}
