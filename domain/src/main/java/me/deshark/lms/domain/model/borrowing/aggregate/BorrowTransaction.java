package me.deshark.lms.domain.model.borrowing.aggregate;


import lombok.Data;
import me.deshark.lms.common.utils.GUID;
import me.deshark.lms.domain.model.borrowing.entity.Patron;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/16 14:42
 */
// 借阅记录聚合根 (包含借阅记录和归还逻辑)
@Data
public class BorrowTransaction {
    private final UUID transactionId;
    private final UUID bookCopyId;
    private final Patron patron;
    private final LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate endDate;
    private String status;

    // 构造方法（创建借阅记录）
    public BorrowTransaction(UUID bookCopyId, Patron patron, LocalDate startDate) {
        this.transactionId = GUID.v7();
        this.bookCopyId = bookCopyId;
        this.patron = patron;
        this.startDate = startDate;
    }

    // 检查是否可以续借
    public boolean canRenew() {
        return "BORROWED".equals(status) && LocalDate.now().isBefore(dueDate);
    }

    public void initializeDueDate() {
        this.dueDate = calculateDueDate(startDate);
        this.status = "BORROWED";
    }
    private LocalDate calculateDueDate(LocalDate baseDate) {
        return baseDate.plusDays(14);
    }
    public void renew() {
        if (!canRenew()) {
            throw new IllegalStateException("Cannot renew this transaction");
        }
        this.dueDate = calculateDueDate(dueDate);
    }
}
