package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 图书信息实体类
 * 
 * <p>对应数据库book表结构，用于MyBatis数据映射</p>
 * 
 * @author DE_SHARK
 * @date 2025/2/26 19:07
 */
@Data
@Builder
public class BookDO {
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
}
