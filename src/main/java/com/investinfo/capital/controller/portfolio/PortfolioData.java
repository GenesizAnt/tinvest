package com.investinfo.capital.controller.portfolio;

import com.investinfo.capital.controller.FormatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.BrokerReportResponse;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

import java.util.List;

import static com.investinfo.capital.controller.MathUtils.getPercentage;

@Component
@RequiredArgsConstructor
public class PortfolioData {

    private final FormatUtils msgData;

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
                msgData.formantNumber(portfolio.getTotalAmountPortfolio().getValue()),
                msgData.formantNumber(portfolio.getTotalAmountShares().getValue()),
                msgData.formantNumber(portfolio.getTotalAmountBonds().getValue()),
                msgData.formantNumber(portfolio.getTotalAmountEtfs().getValue()),
                msgData.formantNumber(portfolio.getTotalAmountCurrencies().getValue()),
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
        positionWithoutBonds.forEach(position -> result.append(msgData.getInfoPosition(position)));
        return result.toString();
    }

    public String getDiagramSectorData(Portfolio portfolio) {
        List<Position> allPositions = portfolio.getPositions();
        List<Position> positionWithoutBonds = allPositions.stream().filter(position -> position.getInstrumentType().equals("share")).toList();
        return msgData.getSectorData(positionWithoutBonds);
    }

    public String getReportPeriod(BrokerReportResponse reportResponse) {
        System.out.println(reportResponse);
        return null;
    }

    //ToDo с НГ сделать метод по доходности (годовой и вообще)
}
