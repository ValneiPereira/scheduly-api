package com.scheduly.api.domain.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyConverter {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    public static BigDecimal toDomain(BigDecimal cents) {
        if (cents == null) return null;
        return cents.divide(ONE_HUNDRED, 2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toCents(BigDecimal amount) {
        if (amount == null) return null;

        return amount
                .multiply(ONE_HUNDRED)
                .setScale(0, RoundingMode.HALF_EVEN);
    }
}


