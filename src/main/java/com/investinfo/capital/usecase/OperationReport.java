package com.investinfo.capital.usecase;

import com.investinfo.capital.dto.MoneyValueDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Operation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OperationReport {

    private final OperationMessageReport operationMessageReport;

    public String getReportForPeriod(List<Operation> operations) {
        Map<String, BigDecimal> reportData = new HashMap<>();
        for (Operation operation : operations) {
            MoneyValueDTO moneyValue = new MoneyValueDTO(operation.getPayment());
            if (operation.getState().getNumber() == 1) {
                if (reportData.containsKey(operation.getType())) {
                    reportData.computeIfPresent(operation.getType(), (payment, sumOperation) -> sumOperation.add(moneyValue.getMoneyValue()));
                } else {
                    reportData.put(operation.getType(), moneyValue.getMoneyValue());
                }
            }
        }
        return operationMessageReport.getReportForPeriod(reportData);
    }
}
