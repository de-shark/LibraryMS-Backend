package me.deshark.lms.domain.model.catalog.entity;

import lombok.Builder;
import lombok.Data;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

import java.util.UUID;

/**
 * 领域实体：图书副本
 * @author DE_SHARK
 * @date 2025/2/16 14:44
 */
@Data
@Builder
public class BookCopy {
    private final UUID copyId;
    private final Isbn isbn;
    private String status;
    private int loanCount;
}
