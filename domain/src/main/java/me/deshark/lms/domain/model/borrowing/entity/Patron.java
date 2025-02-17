package me.deshark.lms.domain.model.borrowing.entity;

import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/15 22:51
 */
public class Patron {
    // 账号 id
    private final UUID id;
    // 目前已借阅数量
    private int currentBorrows;
    // 信用分
    private int creditScore;
    
    private static final int MAX_BORROWS = 5;
    private static final int MIN_CREDIT_SCORE = 60;

    public Patron(UUID id, int currentBorrows, int creditScore) {
        this.id = id;
        this.currentBorrows = currentBorrows;
        this.creditScore = creditScore;
    }

    public boolean canBorrow() {
        return currentBorrows < MAX_BORROWS && creditScore >= MIN_CREDIT_SCORE;
    }

    public void incrementCurrentBorrows() {
        this.currentBorrows++;
    }

    public void decrementCurrentBorrows() {
        this.currentBorrows--;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public int getCurrentBorrows() {
        return currentBorrows;
    }

    public int getCreditScore() {
        return creditScore;
    }
}
