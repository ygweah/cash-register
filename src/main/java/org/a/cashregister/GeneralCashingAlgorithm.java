package org.a.cashregister;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

public class GeneralCashingAlgorithm implements CashingAlgorithm {

    public Cash withdraw(Cash cash, BigDecimal amount) {
        if (cash == null)
            throw new IllegalArgumentException("Cash is required.");

        if (BigDecimal.ZERO.compareTo(amount) > 0)
            throw new IllegalArgumentException("Amount cannot be negative.");

        Queue<BigDecimal> denominationQueue = new LinkedList<>(cash.getDenominationValues());
        Cash withdrawn = new Cash();

        BigDecimal remaining = processRecursively(amount, cash, withdrawn, denominationQueue);
        if (!isZeroAmount(remaining)) {
            return null;
        }

        cash.take(withdrawn);
        return withdrawn;
    }

    private BigDecimal processRecursively(BigDecimal amount, Cash fromCash, Cash toCash, Queue<BigDecimal> denominationValues) {
        if (isZeroAmount(amount) || denominationValues.isEmpty())
            return amount;

        BigDecimal denominationValue = denominationValues.poll();
        CashValue cashValue = fromCash.getCashValue(denominationValue);
        if (cashValue.isZeroAmount())
            return processRecursively(amount, fromCash, toCash, denominationValues);

        int count = amount.divide(denominationValue, 0, BigDecimal.ROUND_FLOOR).intValue();
        count = Math.min(count, cashValue.getCount());

        if (count > 0) {
            for (int withdrawnCount = count; withdrawnCount > 0; --withdrawnCount) {
                // five or quarter are used
                // we make copies to make it easy to backtrack
                Cash toCashCopy = toCash.clone();
                Queue<BigDecimal> denominationValuesCopy = new LinkedList<>(denominationValues);

                CashValue withdrawn = new CashValue(denominationValue, withdrawnCount);
                toCashCopy.add(withdrawn);
                BigDecimal remaining = processRecursively(amount.subtract(withdrawn.getAmount()), fromCash, toCashCopy, denominationValuesCopy);

                if (isZeroAmount(remaining)) {
                    // need copy data
                    toCash.clear();
                    toCash.put(toCashCopy);
                    return remaining;
                }
            }
        }

        return processRecursively(amount, fromCash, toCash, denominationValues);
    }

    private boolean isZeroAmount(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount) == 0;
    }

}
