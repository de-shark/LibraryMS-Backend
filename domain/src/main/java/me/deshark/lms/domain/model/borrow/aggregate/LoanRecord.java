package me.deshark.lms.domain.model.borrow.aggregate;


import lombok.Data;
import me.deshark.lms.common.utils.GUID;
import me.deshark.lms.domain.model.borrow.entity.Patron;
import me.deshark.lms.domain.model.borrow.vo.LoanPeriod;
import me.deshark.lms.domain.model.borrow.vo.LoanStatus;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/16 14:42
 */
// 借阅记录聚合根 (包含借阅记录和归还逻辑)
@Data
public class LoanRecord {
    private final UUID transactionId;
    private final BookCopy bookCopy;
    private final Patron patron;
    private LoanPeriod loanPeriod;
    private LoanStatus status;

    // 构造方法（创建借阅记录）
    public LoanRecord(BookCopy bookCopy, Patron patron) {
        this.transactionId = GUID.v7();
        this.bookCopy = bookCopy;
        this.patron = patron;
        this.loanPeriod = new LoanPeriod(OffsetDateTime.now());
        this.status = LoanStatus.BORROWED;
    }

    // 检查是否可以续借
    public boolean canRenew() {
        return true;
    }

    public void renew() {
        if (!canRenew()) {
            throw new IllegalStateException("Cannot renew this transaction");
        }
        loanPeriod.renew();
    }

    public void returnBook() {
        this.status = LoanStatus.RETURNED;
        loanPeriod.returnBook();
    }
}
