package me.deshark.lms.domain.model.borrowing.aggregate;


import lombok.Data;
import me.deshark.lms.common.utils.GUID;
import me.deshark.lms.domain.model.borrowing.entity.Patron;

import java.util.Date;
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
    private final Date startDate;
    private Date dueDate;
    private Date endDate;
    private String status;

    // 构造方法（创建借阅记录）
    public BorrowTransaction(UUID bookCopyId, Patron patron, Date startDate) {
        this.transactionId = GUID.v7();
        this.bookCopyId = bookCopyId;
        this.patron = patron;
        this.startDate = startDate;
    }

    // 检查是否可以续借
    public boolean canRenew() {
        return false;
    }

    public void initializeDueDate() {
        this.dueDate = calculateDueDate(startDate);
        this.status = "BORROWED";
    }
    private Date calculateDueDate(Date baseDate) {
        return new Date(baseDate.getTime() + 14L * 24 * 60 * 60 * 1000);
    }
    public void renew() {
        if (!canRenew()) {
            throw new IllegalStateException("Cannot renew this transaction");
        }
        this.dueDate = calculateDueDate(dueDate);
    }
}
