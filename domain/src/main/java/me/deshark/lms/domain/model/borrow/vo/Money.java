package me.deshark.lms.domain.model.borrow.vo;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * 金钱值对象（不可变）
 * @author DE_SHARK
 * @date 2025/2/17 22:35
 */
public class Money {
    private final BigDecimal amount;
    private final Currency currency;

    // 工厂方法 - 创建人民币
    public static Money rmb(BigDecimal amount) {
        return new Money(amount, Currency.getInstance("CNY"));
    }

    // 工厂方法 - 创建指定货币
    public static Money money(BigDecimal amount, String currency) {
        return new Money(amount, Currency.getInstance(currency));
    }

    private Money(BigDecimal amount, Currency currency) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("金额不能为负数");
        }
        this.amount = amount.setScale(currency.getDefaultFractionDigits());
        this.currency = Objects.requireNonNull(currency);
    }

    // 金额加法
    public Money add(Money other) {
        checkCurrencyMatch(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    // 金额减法
    public Money subtract(Money other) {
        checkCurrencyMatch(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    // 金额乘法
    public Money multiply(Money other) {
        checkCurrencyMatch(other);
        return new Money(this.amount.multiply(other.amount), this.currency);
    }

    private void checkCurrencyMatch(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("货币单位不匹配");
        }
    }

    // 格式化显示
    public String format() {
        return String.format("%s %s", currency.getSymbol(), amount);
    }

    // Getters
    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0 &&
                currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
