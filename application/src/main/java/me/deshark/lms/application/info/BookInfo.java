package me.deshark.lms.application.info;

import lombok.Builder;
import lombok.Data;

/**
 * @author DE_SHARK
 */
@Data
@Builder
public class BookInfo {
    private String isbn;
    private String title;
    private String author;
}
