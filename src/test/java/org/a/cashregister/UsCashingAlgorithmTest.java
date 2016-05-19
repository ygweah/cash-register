package org.a.cashregister;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class UsCashingAlgorithmTest {

    private CashingAlgorithm cashingAlgorithm = new UsCashingAlgorithm();

    @Test
    public void testSingleDenomination() {
        Cash cash = new Cash();
        cash.add(UsDenomination.FIVE_DOLLAR.toCashValue(2));
        cash.add(UsDenomination.TWO_DOLLAR.toCashValue(2));
        cash.add(UsDenomination.ONE_DOLLAR.toCashValue(2));

        BigDecimal withdrawnAmount = new BigDecimal("5.00");
        Cash withdrawnCash = cashingAlgorithm.withdraw(cash, withdrawnAmount);

        Assert.assertNotNull(withdrawnCash);
        Assert.assertEquals(withdrawnCash.getTotalAmount(), withdrawnAmount);
        Assert.assertEquals(withdrawnCash.getCashValue(UsDenomination.FIVE_DOLLAR.getValue()).getCount(), 1);
    }

    @Test
    public void testSuccess() {
        Cash cash = new Cash();
        cash.add(UsDenomination.TWENTY_DOLLAR.toCashValue(2));
        cash.add(UsDenomination.FIVE_DOLLAR.toCashValue(2));
        cash.add(UsDenomination.TWO_DOLLAR.toCashValue(2));
        cash.add(UsDenomination.ONE_DOLLAR.toCashValue(2));

        BigDecimal withdrawnAmount = new BigDecimal("28.00");
        Cash withdrawnCash = cashingAlgorithm.withdraw(cash, withdrawnAmount);

        Assert.assertNotNull(withdrawnCash);
        Assert.assertEquals(withdrawnCash.getTotalAmount(), withdrawnAmount);
        Assert.assertEquals(withdrawnCash.getCashValue(UsDenomination.TWENTY_DOLLAR.getValue()).getCount(), 1);
        Assert.assertEquals(withdrawnCash.getCashValue(UsDenomination.FIVE_DOLLAR.getValue()).getCount(), 1);
        Assert.assertEquals(withdrawnCash.getCashValue(UsDenomination.TWO_DOLLAR.getValue()).getCount(), 1);
        Assert.assertEquals(withdrawnCash.getCashValue(UsDenomination.ONE_DOLLAR.getValue()).getCount(), 1);
    }

    @Test
    public void testInsufficientLargeBill() {
        Cash cash = new Cash();
        cash.add(UsDenomination.TWO_DOLLAR.toCashValue(1));
        cash.add(UsDenomination.ONE_DOLLAR.toCashValue(5));

        BigDecimal withdrawnAmount = new BigDecimal("5.00");
        Cash withdrawnCash = cashingAlgorithm.withdraw(cash, withdrawnAmount);

        Assert.assertNotNull(withdrawnCash);
        Assert.assertEquals(withdrawnCash.getTotalAmount(), withdrawnAmount);
        Assert.assertEquals(withdrawnCash.getCashValue(UsDenomination.TWO_DOLLAR.getValue()).getCount(), 1);
        Assert.assertEquals(withdrawnCash.getCashValue(UsDenomination.ONE_DOLLAR.getValue()).getCount(), 3);
    }

    @Test
    public void testTwoDollarEdgeCase() {
        Cash cash = new Cash();
        cash.add(UsDenomination.TWENTY_DOLLAR.toCashValue(2));
        cash.add(UsDenomination.FIVE_DOLLAR.toCashValue(2));
        cash.add(UsDenomination.TWO_DOLLAR.toCashValue(5));

        BigDecimal withdrawnAmount = new BigDecimal("28.00");
        Cash withdrawnCash = cashingAlgorithm.withdraw(cash, withdrawnAmount);

        Assert.assertNotNull(withdrawnCash);
        Assert.assertEquals(withdrawnCash.getTotalAmount(), withdrawnAmount);
        Assert.assertEquals(withdrawnCash.getCashValue(UsDenomination.TWENTY_DOLLAR.getValue()).getCount(), 1);
        Assert.assertEquals(withdrawnCash.getCashValue(UsDenomination.TWO_DOLLAR.getValue()).getCount(), 4);
    }

}
