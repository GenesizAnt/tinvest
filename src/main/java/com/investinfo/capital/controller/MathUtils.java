package com.investinfo.capital.controller;

import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class MathUtils {

    public static String getPercentage(BigDecimal amount, BigDecimal portion) {
        return String.format("%.2f%%", portion.divide(amount, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
    }

    public static BigDecimal getSumPositions(List<Position> position) {
        BigDecimal currSumPosition = BigDecimal.ZERO;
        for (Position currPosition : position) {
            currSumPosition = currSumPosition.add(currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity()));
        }
        return currSumPosition;
    }
}
