package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;
import me.deshark.lms.infrastructure.enums.LoanStatusType;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Data
@Builder
public class LoanRecordDO {
    private UUID loanId;
    private UUID bookCopyId;
    private UUID userId;
    private OffsetDateTime loanDate;
    private OffsetDateTime dueDate;
    private OffsetDateTime returnDate;
    private LoanStatusType status;
}
