package me.deshark.lms.application.info;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @author DE_SHARK
 */
@Data
@Builder
public class BorrowRecord {
    private String isbn;
    private String title;
    private String author;
    private OffsetDateTime borrowDate;
    private OffsetDateTime dueDate;
    private OffsetDateTime returnDate;
    private String status;
}
