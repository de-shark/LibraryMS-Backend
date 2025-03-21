package me.deshark.lms.application.info;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Data
@Builder
public class BorrowRecord {
    private UUID recordId;
    private String isbn;
    private String title;
    private String author;
    private OffsetDateTime borrowDate;
    private OffsetDateTime dueDate;
    private OffsetDateTime returnDate;
    private String status;
}
