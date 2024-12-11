package com.investinfo.capital.usecase;

import com.investinfo.capital.dto.ImoexPositionDTO;
import com.investinfo.capital.service.ImoexPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.investinfo.capital.usecase.utils.FormatNumberUtils.formantNumber;
import static com.investinfo.capital.usecase.utils.MathUtils.getPercentageString;
import static com.investinfo.capital.usecase.utils.MathUtils.getSumPositions;

@RequiredArgsConstructor
@Component
public class PortfolioMessageReport {

    private final ImoexPositionService positionService;

    public String getPortfolioOverview(Portfolio portfolio) {
        return """
                Общая сумма: %s
                
                Сумма акций: %s
                Сумма облигаций: %s
                Сумма золота: %s
                Кэш: %s
                
                Доля акций: %s
                Доля облигаций: %s
                Доля золота: %s
                Кэш: %s
                """.formatted(
                formantNumber(portfolio.getTotalAmountPortfolio().getValue()),
                formantNumber(portfolio.getTotalAmountShares().getValue()),
                formantNumber(portfolio.getTotalAmountBonds().getValue()),
                formantNumber(portfolio.getTotalAmountEtfs().getValue()),
                formantNumber(portfolio.getTotalAmountCurrencies().getValue()),
                getPercentageString(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountShares().getValue()),
                getPercentageString(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountBonds().getValue()),
                getPercentageString(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountEtfs().getValue()),
                getPercentageString(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountCurrencies().getValue())
        );
    }

    public String getInfoPosition(List<Position> position) {
        StringBuilder textMessage = new StringBuilder();
        for (Position security : position) {
            ImoexPositionDTO share = positionService.getImoexPositionDTO(security.getFigi());
            textMessage.append(
                    """
                            %-10s | Текущая цена: %-8s | Доходность: %-6s
                            
                            """.formatted(
                            share.getShortName(),
                            formantNumber(security.getCurrentPrice().getValue()),
                            getPercentageString(
                                    security.getAveragePositionPrice().getValue(),
                                    security.getCurrentPrice().getValue().subtract(security.getAveragePositionPrice().getValue()))
                    ));
        }
        return textMessage.toString();
    }

    public String getSectorData(List<Position> positions) {
        Map<String, BigDecimal> sectorData = getDataFromPosition(positions);
        BigDecimal currSumPosition = getSumPositions(positions);
        return getSectorDataMsg(sectorData, currSumPosition);
    }

    public String getMsgEveryDayEndReport(Map<String, String[]> growthPercentage) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String[]> position : growthPercentage.entrySet()) {
            result.append(

                    """
                            %-10s | Цена: %-8s | Рост: %-6s
                            
                            """.formatted(
                            position.getKey(),
                            position.getValue()[0],
                            position.getValue()[1]
                    )
            );
        }
        return result.toString();
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
            ImoexPositionDTO share = positionService.getImoexPositionDTO(currPosition.getFigi());
            if (sectorData.containsKey(share.getSector())) {
                sectorData.computeIfPresent(share.getSector(), (price, sumSector) -> sumSector.add(currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity())));
            } else {
                sectorData.put(share.getSector(), currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity()));
            }
        }
        return sectorData;
    }
}
