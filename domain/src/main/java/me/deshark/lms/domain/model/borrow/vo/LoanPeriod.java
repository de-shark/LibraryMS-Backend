package me.deshark.lms.domain.model.borrow.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

/**
 * @author DE_SHARK
 */
@Getter
@AllArgsConstructor
@Builder
public class LoanPeriod {
    private final OffsetDateTime loanDate;
    private OffsetDateTime dueDate;
    private OffsetDateTime returnDate;

    public LoanPeriod(OffsetDateTime loanDate) {
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusDays(14);
    }

    public void renew() {
        this.dueDate = dueDate.plusDays(14);
    }

    public void returnBook() {
        this.returnDate = OffsetDateTime.now();
    }
}
