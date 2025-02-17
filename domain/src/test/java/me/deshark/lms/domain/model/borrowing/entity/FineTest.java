package me.deshark.lms.domain.model.borrowing.entity;

import com.github.f4b6a3.uuid.alt.GUID;
import me.deshark.lms.domain.model.borrowing.vo.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author DE_SHARK
 * @date 2025/2/17 23:14
 */
class FineTest {
    private Fine fine;

    @BeforeEach
    void setup() {
        fine = new Fine(GUID.v7().toUUID());
    }

    @Test
    void shouldInitializeWithPendingStatus() {
        assertEquals("PENDING", fine.getStatus());
    }

    @Test
    void markAsPaidShouldChangeStatus() {
        fine.markAsPaid();
        assertEquals("PAID", fine.getStatus());
    }

    @Test
    void markAsPaidShouldFailWhenNotPending() {
        fine.markAsPaid();
        assertThrows(IllegalStateException.class,
                () -> fine.markAsPaid());
    }

    @Test
    void calculateOverdueFeeShouldSetReasonAndAmount() {
        Money dailyRate = Money.rmb(new BigDecimal("5"));
        fine.calculateOverdueFee(3, dailyRate);

        assertEquals("图书逾期罚款（3天）", fine.getReason());
        assertEquals(new BigDecimal("15.00"), fine.getAmount().getAmount());
    }
}
