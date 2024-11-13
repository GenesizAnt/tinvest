package com.investinfo.capital.controller.portfolio;

import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;
import ru.tinkoff.piapi.core.models.SecurityPosition;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import static com.investinfo.capital.controller.FormatUtils.formantNumber;
import static com.investinfo.capital.controller.MathUtils.getPercentage;

@Component
public class PortfolioData {
    public String getPortfolio(Portfolio portfolio) {
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
                        getPercentage(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountShares().getValue()),
                        getPercentage(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountBonds().getValue()),
                        getPercentage(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountEtfs().getValue()),
                        getPercentage(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountCurrencies().getValue())
        );
    }

    public String getPositionWithoutBonds(Portfolio portfolio) {
        List<Position> allPositions = portfolio.getPositions();
        List<Position> positionWithoutBonds = allPositions.stream().filter(position -> !position.getInstrumentType().equals("bond") && !position.getInstrumentType().equals("currency")).toList();
        StringBuilder result = new StringBuilder();
        positionWithoutBonds.forEach(position -> result.append(getInfoPosition(position)));
        return result.toString();
    }

    private String getInfoPosition(Position position) {
        Random random = new Random();
        BigDecimal expectedYield = position.getExpectedYield();
        BigDecimal value = position.getCurrentPrice().getValue();
        BigDecimal subtract = position.getExpectedYield().subtract(position.getCurrentPrice().getValue());
        return """
                %-10s: | Текущая цена: %-8s | Доходность: %-6s
                
                """.formatted(
                        "a".repeat(random.nextInt(12) + 1),
                formantNumber(position.getCurrentPrice().getValue()),
                getPercentage(
                        position.getAveragePositionPrice().getValue(),
                        position.getAveragePositionPrice().getValue().subtract(position.getCurrentPrice().getValue()))
        );
    }


    //ToDo с НГ сделать метод по доходности (годовой и вообще)
}
