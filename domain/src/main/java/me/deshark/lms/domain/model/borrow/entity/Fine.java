package me.deshark.lms.domain.model.borrow.entity;

import me.deshark.lms.domain.model.borrow.vo.Money;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 罚款实体（贫血模型）
 * @author DE_SHARK
 * @date 2025/2/17 22:28
 */
public class Fine {
    private final UUID id;
    private String reason;
    private Money amount;
    private String status;

    public Fine(UUID id) {
        this.id = id;
        // 初始状态
        this.status = "PENDING";
    }

    // 业务方法：计算滞纳金
    public void calculateOverdueFee(int overdueDays, Money dailyRate) {
        if (!"PENDING".equals(status)) {
            throw new IllegalStateException("只有待处理状态的罚款可以计算");
        }
        this.amount = dailyRate.multiply(Money.rmb(BigDecimal.valueOf(overdueDays)));
        this.reason = "图书逾期罚款（" + overdueDays + "天）";
    }

    // 状态变更方法
    public void markAsPaid() {
        if (!"PENDING".equals(status)) {
            throw new IllegalStateException("只有待处理状态的罚款可以标记为已支付");
        }
        this.status = "PAID";
    }

    public UUID getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public Money getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}
