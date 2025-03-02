package me.deshark.lms.domain.model.borrowing.aggregate;


import me.deshark.lms.common.utils.GUID;
import me.deshark.lms.domain.model.borrowing.entity.Patron;

import java.util.Date;
import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/16 14:42
 */
// 借阅记录聚合根 (包含借阅记录和归还逻辑)
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
        return "BORROWED".equals(status) && 
               new Date().before(endDate) && 
               patron.canBorrow();
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getBookCopyId() {
        return bookCopyId;
    }

    public Patron getPatron() {
        return patron;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
