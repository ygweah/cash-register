package org.a.cashregister;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class GeneralCashingAlgorithmTest {

    private CashingAlgorithm cashingAlgorithm = new GeneralCashingAlgorithm();

    @Test
    public void testSingleDenomination() {
        Cash cash = new Cash();
        cash.add(new CashValue(new BigDecimal("17.00"), 1));
        cash.add(new CashValue(new BigDecimal("13.00"), 1));
        cash.add(new CashValue(new BigDecimal("11.00"), 1));

        BigDecimal withdrawnAmount = new BigDecimal("13.00");
        Cash withdrawnCash = cashingAlgorithm.withdraw(cash, withdrawnAmount);

        Assert.assertNotNull(withdrawnCash);
        Assert.assertEquals(withdrawnCash.getTotalAmount(), withdrawnAmount);
        Assert.assertEquals(withdrawnCash.getCashValue(new BigDecimal("13.00")).getCount(), 1);
    }

    @Test
    public void testSuccess() {
        Cash cash = new Cash();
        cash.add(new CashValue(new BigDecimal("20.00"), 2));
        cash.add(new CashValue(new BigDecimal("5.00"), 2));
        cash.add(new CashValue(new BigDecimal("2.00"), 2));
        cash.add(new CashValue(new BigDecimal("1.00"), 2));

        BigDecimal withdrawnAmount = new BigDecimal("28.00");
        Cash withdrawnCash = cashingAlgorithm.withdraw(cash, withdrawnAmount);

        Assert.assertNotNull(withdrawnCash);
        Assert.assertEquals(withdrawnCash.getTotalAmount(), withdrawnAmount);
        Assert.assertEquals(withdrawnCash.getCashValue(new BigDecimal("20.00")).getCount(), 1);
        Assert.assertEquals(withdrawnCash.getCashValue(new BigDecimal("5.00")).getCount(), 1);
        Assert.assertEquals(withdrawnCash.getCashValue(new BigDecimal("2.00")).getCount(), 1);
        Assert.assertEquals(withdrawnCash.getCashValue(new BigDecimal("1.00")).getCount(), 1);
    }

    @Test
    public void testInsufficientLargeBill() {
        Cash cash = new Cash();
        cash.add(new CashValue(new BigDecimal("2.00"), 1));
        cash.add(new CashValue(new BigDecimal("1.00"), 5));

        BigDecimal withdrawnAmount = new BigDecimal("5.00");
        Cash withdrawnCash = cashingAlgorithm.withdraw(cash, withdrawnAmount);

        Assert.assertNotNull(withdrawnCash);
        Assert.assertEquals(withdrawnCash.getTotalAmount(), withdrawnAmount);
        Assert.assertEquals(withdrawnCash.getCashValue(new BigDecimal("2.00")).getCount(), 1);
        Assert.assertEquals(withdrawnCash.getCashValue(new BigDecimal("1.00")).getCount(), 3);
    }

    @Test
    public void testTwoDollarEdgeCase() {
        Cash cash = new Cash();
        cash.add(new CashValue(new BigDecimal("20.00"), 2));
        cash.add(new CashValue(new BigDecimal("5.00"), 2));
        cash.add(new CashValue(new BigDecimal("2.00"), 5));

        BigDecimal withdrawnAmount = new BigDecimal("28.00");
        Cash withdrawnCash = cashingAlgorithm.withdraw(cash, withdrawnAmount);

        Assert.assertNotNull(withdrawnCash);
        Assert.assertEquals(withdrawnCash.getTotalAmount(), withdrawnAmount);
        Assert.assertEquals(withdrawnCash.getCashValue(new BigDecimal("20.00")).getCount(), 1);
        Assert.assertEquals(withdrawnCash.getCashValue(new BigDecimal("2.00")).getCount(), 4);
    }

}
