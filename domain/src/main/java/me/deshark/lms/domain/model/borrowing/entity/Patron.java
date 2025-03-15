package me.deshark.lms.domain.model.borrowing.entity;

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
    // 目前已借阅数量
    private int currentBorrows;
    // 信用分
    private int creditScore;
    
    private static final int MAX_BORROWS = 5;
    private static final int MIN_CREDIT_SCORE = 60;

    public void canBorrow() {
        if (currentBorrows >= MAX_BORROWS) {
            throw new IllegalArgumentException("Max borrows reached");
        }
    }
}
