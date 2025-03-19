package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;
import me.deshark.lms.infrastructure.enums.CopyStatusType;

import java.util.Date;
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
    private String location;
    private CopyStatusType status;
    private int loanCount;
    private Date acquisitionDate;
}
