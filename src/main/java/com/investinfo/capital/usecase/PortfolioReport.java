package com.investinfo.capital.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.util.List;

import static com.investinfo.capital.usecase.utils.FormatNumberUtils.formantNumber;
import static com.investinfo.capital.usecase.utils.MathUtils.getPercentage;
import static com.investinfo.capital.usecase.utils.MathUtils.getPercentageString;

@Component
@RequiredArgsConstructor
public class PortfolioReport {

    private final PortfolioMessageReport messageReport;

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

    public String getPositionsWithoutBonds(Portfolio portfolio) {
        List<Position> allPositions = portfolio.getPositions();
        List<Position> positionWithoutBonds = allPositions.stream()
                .filter(position -> !position.getInstrumentType().equals("bond") && !position.getInstrumentType().equals("currency"))
                .sorted((p1, p2) -> {
                    BigDecimal yield1 = getPercentage(
                            p1.getAveragePositionPrice().getValue(),
                            p1.getCurrentPrice().getValue().subtract(p1.getAveragePositionPrice().getValue())
                    );
                    BigDecimal yield2 = getPercentage(
                            p2.getAveragePositionPrice().getValue(),
                            p2.getCurrentPrice().getValue().subtract(p2.getAveragePositionPrice().getValue())
                    );
                    return yield2.compareTo(yield1);
                })
                .toList();
        StringBuilder textMessage = new StringBuilder();
        positionWithoutBonds.forEach(position -> textMessage.append(messageReport.getInfoPosition(position)));
        return textMessage.toString();
    }
}
