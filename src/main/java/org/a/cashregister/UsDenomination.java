package org.a.cashregister;

import java.math.BigDecimal;

/**
 * US denominations. Note: the scale must be set to 2.
 */
public enum UsDenomination {
    ONE_HUNDRED_DOLLAR(new BigDecimal("100.00")),
    FIFTY_DOLLAR(new BigDecimal("50.00")),
    TWENTY_DOLLAR(new BigDecimal("20.00")),
    TEN_DOLLAR(new BigDecimal("10.00")),
    FIVE_DOLLAR(new BigDecimal("5.00")),
    TWO_DOLLAR(new BigDecimal("2.00")),
    ONE_DOLLAR(new BigDecimal("1.00")),
    HALF_DOLLAR(new BigDecimal("0.50")),
    QUARTER(new BigDecimal("0.25")),
    DIME(new BigDecimal("0.10")),
    NICKEL(new BigDecimal("0.05")),
    PENNY(new BigDecimal("0.01"));

    public static final UsDenomination findByValue(BigDecimal value) {
        for (UsDenomination denomination : values()) {
            if (denomination.getValue().equals(value))
                return denomination;
        }
        return null;
    }

    private BigDecimal value;

    UsDenomination(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public CashValue toCashValue(int count) {
        return new CashValue(value, count);
    }

}
