package org.a.cashregister;

import java.math.BigDecimal;

interface CashingAlgorithm {

    public Cash withdraw(Cash cash, BigDecimal amount);

}
