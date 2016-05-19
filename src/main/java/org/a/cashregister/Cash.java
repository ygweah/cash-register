package org.a.cashregister;

import java.math.BigDecimal;
import java.util.*;

/**
 * A data structure to hold a collection of denomination values.
 */
public class Cash implements Cloneable {

    private Map<BigDecimal, CashValue> cashValueByDenomination;

    public Cash(Collection<CashValue> cashValues) {
        this();
        for (CashValue cashValue : cashValues) {
            cashValueByDenomination.put(cashValue.getDenominationValue(), cashValue.clone());
        }
    }

    public Cash() {
        this.cashValueByDenomination = new TreeMap<>(Collections.<BigDecimal>reverseOrder());
    }

    public List<BigDecimal> getDenominationValues() {
        return new LinkedList<>(cashValueByDenomination.keySet());
    }

    public List<CashValue> getCashValues() {
        List<CashValue> cashValues = new LinkedList<>();
        for (CashValue cashValue : cashValueByDenomination.values()) {
            cashValues.add(cashValue.clone());
        }
        return cashValues;
    }

    public CashValue getCashValue(BigDecimal denominationValue) {
        return cashValueByDenomination.get(denominationValue).clone();
    }

    public BigDecimal getTotalAmount() {
        BigDecimal sum = BigDecimal.ZERO;
        for (CashValue cashValue : cashValueByDenomination.values()) {
            sum = sum.add(cashValue.getAmount());
        }
        return sum;
    }

    public void add(CashValue plus) {
        CashValue cashValue = cashValueByDenomination.get(plus.getDenominationValue());
        if (cashValue == null) {
            cashValueByDenomination.put(plus.getDenominationValue(), plus.clone());
        } else {
            cashValue.add(plus.getCount());
        }
    }

    public void substract(CashValue minus) {
        CashValue cashValue = cashValueByDenomination.get(minus.getDenominationValue());
        if (cashValue == null || cashValue.getCount() < minus.getCount())
            throw new IllegalStateException("Not enough cash: " + minus);
        cashValue.substract(minus.getCount());
    }

    public void put(Cash removed) {
        for (CashValue removedCashValue : removed.cashValueByDenomination.values()) {
            add(removedCashValue);
        }
    }

    public void take(Cash removed) {
        Cash copy = this.clone();
        for (CashValue removedCashValue : removed.cashValueByDenomination.values()) {
            copy.substract(removedCashValue);
        }
        this.cashValueByDenomination = copy.cashValueByDenomination;
    }

    public void clear() {
        cashValueByDenomination.clear();
    }

    @Override
    public Cash clone() {
        Cash copy = new Cash();
        for (Map.Entry<BigDecimal, CashValue> mapEntry : cashValueByDenomination.entrySet()) {
            copy.cashValueByDenomination.put(mapEntry.getKey(), mapEntry.getValue().clone());
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CashValue cashValue : cashValueByDenomination.values()) {
            if (sb.length() > 0)
                sb.append(' ');
            sb.append(cashValue);
        }
        return sb.toString();
    }

}
