package com.investinfo.capital.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static String getPercentage(BigDecimal amount, BigDecimal portion) {
        return String.format("%.2f%%", portion.divide(amount, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
    }
}
