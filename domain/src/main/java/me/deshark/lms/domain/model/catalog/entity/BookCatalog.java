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
public class BookCatalog {
    private final Isbn isbn;
    private String title;
    private String author;
}
