package com.investinfo.capital.controller;

import com.investinfo.capital.dto.PositionDTO;
import com.investinfo.capital.service.ImoexPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.investinfo.capital.controller.MathUtils.getPercentage;

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
        PositionDTO share = positionService.getShareDTO(position.getFigi());
        return """
                %-10s | Текущая цена: %-8s | Доходность: %-6s
                
                """.formatted(
                share.getShortName(),
                formantNumber(position.getCurrentPrice().getValue()),
                getPercentage(
                        position.getAveragePositionPrice().getValue(),
                        position.getCurrentPrice().getValue().subtract(position.getAveragePositionPrice().getValue()))
        );
    }

    public String getSectorData(List<Position> position) {


        Map<String, BigDecimal> sectorData = new HashMap<>();

        BigDecimal currSumPosition = BigDecimal.ZERO;

        for (Position currPosition : position) {
            currSumPosition = currSumPosition.add(currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity()));
        }

        for (Position currPosition : position) {
            PositionDTO share = positionService.getShareDTO(currPosition.getFigi());
            if (sectorData.containsKey(share.getSector())) {
                sectorData.computeIfPresent(share.getSector(), (price, sumSector) -> sumSector.add(currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity())));
            } else {
                sectorData.put(share.getSector(), currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity()));
            }
        }


        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, BigDecimal> data : sectorData.entrySet()) {
            String formattedLine = String.format("%-15s   %.2f%%", data.getKey().equals("it") ? data.getKey() + "       *****" : data.getKey(), data.getValue().divide(currSumPosition, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
            result.append(formattedLine).append("\n");
        }

        return result.toString();
    }
}
