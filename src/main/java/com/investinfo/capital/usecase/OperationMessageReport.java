package com.investinfo.capital.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static com.investinfo.capital.usecase.utils.FormatNumberUtils.formantNumber;

@RequiredArgsConstructor
@Component
public class OperationMessageReport {

    public String getReportForPeriod(Map<String, BigDecimal> reportData) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, BigDecimal> data : reportData.entrySet()) {
            String formattedLine = String.format("%-10s : %2s", data.getKey(), formantNumber(data.getValue().setScale(2, RoundingMode.HALF_UP)));
            result.append(formattedLine).append("\n");
        }
        return result.toString();
    }
}
