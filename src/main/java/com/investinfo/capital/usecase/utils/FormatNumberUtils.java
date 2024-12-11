package com.investinfo.capital.usecase.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Component
public class FormatNumberUtils {

    public static String formantNumber(BigDecimal amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        return decimalFormat.format(amount);
    }

    // Удалить символ процента и заменить запятую на точку
    public static double parseGrowthPercentage(String percentage) {
        String cleanedPercentage = percentage.replace("%", "").replace(',', '.');
        return Double.parseDouble(cleanedPercentage);
    }
}
