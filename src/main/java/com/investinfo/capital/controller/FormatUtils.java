package com.investinfo.capital.controller;

import com.investinfo.capital.dto.MoneyValueDTO;
import com.investinfo.capital.dto.ImoexPositionDTO;
import com.investinfo.capital.service.ImoexPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Operation;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.investinfo.capital.controller.MathUtils.getPercentageString;
import static com.investinfo.capital.controller.MathUtils.getSumPositions;

@RequiredArgsConstructor
@Component
public class FormatUtils {

    private final ImoexPositionService positionService;

    public String formantNumber(BigDecimal amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        return decimalFormat.format(amount);
    }

    public String getInfoPosition(Position position) {
        ImoexPositionDTO share = positionService.getShareDTO(position.getFigi());
        return """
                %-10s | Текущая цена: %-8s | Доходность: %-6s
                
                """.formatted(
                share.getShortName(),
                formantNumber(position.getCurrentPrice().getValue()), //ToDo получать все нужно из ДТО
                getPercentageString(
                        position.getAveragePositionPrice().getValue(),
                        position.getCurrentPrice().getValue().subtract(position.getAveragePositionPrice().getValue()))
        );
    }

    public String getSectorData(List<Position> positions) {
        Map<String, BigDecimal> sectorData = getDataFromPosition(positions);
        BigDecimal currSumPosition = getSumPositions(positions);
        return getSectorDataMsg(sectorData, currSumPosition);
    }

    private String getSectorDataMsg(Map<String, BigDecimal> sectorData, BigDecimal currSumPosition) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, BigDecimal> data : sectorData.entrySet()) {
            String formattedLine = String.format("%-15s   %.2f%%", data.getKey().equals("it") ? data.getKey() + "       *****" : data.getKey(), data.getValue().divide(currSumPosition, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
            result.append(formattedLine).append("\n");
        }
        return result.toString();
    }

    private Map<String, BigDecimal> getDataFromPosition(List<Position> position) {
        Map<String, BigDecimal> sectorData = new HashMap<>();
        for (Position currPosition : position) {
            ImoexPositionDTO share = positionService.getShareDTO(currPosition.getFigi());
            if (sectorData.containsKey(share.getSector())) {
                sectorData.computeIfPresent(share.getSector(), (price, sumSector) -> sumSector.add(currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity())));
            } else {
                sectorData.put(share.getSector(), currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity()));
            }
        }
        return sectorData;
    }

    public String getReportPeriod(List<Operation> reportResponse) {
        Map<String, BigDecimal> reportData = getReportDataMap(reportResponse);
        return getResultReportData(reportData);
    }

    private String getResultReportData(Map<String, BigDecimal> reportData) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, BigDecimal> data : reportData.entrySet()) {
            String formattedLine = String.format("%-10s : %2s", data.getKey(), formantNumber(data.getValue().setScale(2, RoundingMode.HALF_UP)));
            result.append(formattedLine).append("\n");
        }
        return result.toString();
    }

    private Map<String, BigDecimal> getReportDataMap(List<Operation> reportResponse) {
        Map<String, BigDecimal> reportData = new HashMap<>();
        for (Operation operation : reportResponse) {
            MoneyValueDTO moneyValue = new MoneyValueDTO(operation.getPayment());
            if (operation.getState().getNumber() == 1) {
                if (reportData.containsKey(operation.getType())) {
                    reportData.computeIfPresent(operation.getType(), (payment, sumOperation) -> sumOperation.add(moneyValue.getMoneyValue()));
                } else {
                    reportData.put(operation.getType(), moneyValue.getMoneyValue());
                }
            }
        }
        return reportData;
    }
}
