package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author DE_SHARK
 * @date 2025/2/26 19:07
 */
@Data
@Builder
public class BookInfoDO {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int publishYear;
    private String description;
    private String coverUrl;
    private Timestamp createdAt;
}
