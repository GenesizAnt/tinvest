package com.investinfo.capital.controller.portfolio;

import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.models.Portfolio;

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


    //ToDo с НГ сделать метод по доходности (годовой и вообще)
}
