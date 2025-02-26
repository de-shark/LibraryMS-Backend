package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;
import me.deshark.lms.domain.model.borrowing.vo.BookCopyStatus;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/26 19:18
 */
@Data
@Builder
public class BookCopyDO {
    private UUID copyId;
    private String isbn;
    private BookCopyStatus status;
    private String location;
    private Date acquisitionDate;
    private int conditionRating;
    private Timestamp lastMaintain;
}
