package org.a.cashregister;

import java.math.BigDecimal;

/**
 * A data structure to store denomination value and its count, which can be cloned.
 */
public class CashValue implements Cloneable {

    private BigDecimal denominationValue;
    private int count;

    public CashValue(BigDecimal denominationValue) {
        this(denominationValue, 0);
    }

    public CashValue(BigDecimal denominationValue, int count) {
        if (denominationValue == null)
            throw new IllegalArgumentException("Denomination is required.");

        if (count < 0)
            throw new IllegalArgumentException("Count cannot be negative: " + count);

        this.denominationValue = denominationValue;
        this.count = count;
    }

    public BigDecimal getDenominationValue() {
        return denominationValue;
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getAmount() {
        return denominationValue.multiply(new BigDecimal(count));
    }

    public boolean isZeroAmount() {
        return count == 0;
    }

    public CashValue add(int plus) {
        if (plus < 0)
            throw new IllegalArgumentException("Count cannot be negative: " + plus);
        count += plus;
        return this;
    }

    public CashValue substract(int minus) {
        if (minus < 0)
            throw new IllegalArgumentException("Count cannot be negative: " + minus);

        if (minus > count)
            throw new IllegalArgumentException("Not enough to be substracted: " + minus);

        count -= minus;
        return this;
    }

    @Override
    public CashValue clone() {
        return new CashValue(denominationValue, count);
    }

    @Override
    public String toString() {
        return String.format("%sx%d", denominationValue, count);
    }

}
