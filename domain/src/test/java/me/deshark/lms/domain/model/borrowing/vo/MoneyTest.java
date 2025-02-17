package me.deshark.lms.domain.model.borrowing.vo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author DE_SHARK
 * @date 2025/2/17 23:03
 */
class MoneyTest {

    @Test
    void shouldThrowWhenNegativeAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> Money.rmb(new BigDecimal("-5")));
    }

    @Test
    void addShouldWorkWithSameCurrency() {
        Money m1 = Money.rmb(new BigDecimal("10.5"));
        Money m2 = Money.rmb(new BigDecimal("20.5"));
        Money result = m1.add(m2);

        assertEquals(new BigDecimal("31.00"), result.getAmount());
        assertEquals("CNY", result.getCurrency().getCurrencyCode());
    }

    @Test
    void addShouldThrowWhenCurrencyMismatch() {
        Money rmb = Money.rmb(BigDecimal.TEN);
        Money usd = Money.money(BigDecimal.ONE, "USD");

        assertThrows(IllegalArgumentException.class,
                () -> rmb.add(usd));
    }

    @Test
    void equalsShouldCompareValueAndCurrency() {
        Money m1 = Money.rmb(new BigDecimal("100.00"));
        Money m2 = Money.rmb(new BigDecimal("100.00"));
        Money m3 = Money.money(new BigDecimal("100.00"), "JPY");

        assertEquals(m1, m2);
        assertNotEquals(m1, m3);
    }
}
