package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Data
@Builder
public class BorrowerInfoDO {
    private UUID userId;
    private int maxBorrowLimit;
    private int currentLoans;
}
