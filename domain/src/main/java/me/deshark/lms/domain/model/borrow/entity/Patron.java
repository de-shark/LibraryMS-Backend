package me.deshark.lms.domain.model.borrow.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/15 22:51
 */
@Data
@Builder
public class Patron {
    // 账号 id
    private final UUID id;
    /**
     * 账号借阅上限
     */
    private int maxBorrowLimit;
    // 目前已借阅数量
    private int currentBorrows;

    public void canBorrow() {
        if (currentBorrows >= maxBorrowLimit) {
            throw new IllegalArgumentException("Max borrows reached");
        }
    }
}
