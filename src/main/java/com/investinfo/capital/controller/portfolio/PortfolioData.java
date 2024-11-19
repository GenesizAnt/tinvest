package com.investinfo.capital.controller.portfolio;

import com.investinfo.capital.controller.FormatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Operation;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.util.List;

import static com.investinfo.capital.controller.MathUtils.getPercentage;
import static com.investinfo.capital.controller.MathUtils.getPercentageString;

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
                getPercentageString(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountShares().getValue()),
                getPercentageString(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountBonds().getValue()),
                getPercentageString(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountEtfs().getValue()),
                getPercentageString(portfolio.getTotalAmountPortfolio().getValue(), portfolio.getTotalAmountCurrencies().getValue())
        );
    }

    public String getPositionWithoutBonds(Portfolio portfolio) {
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
        StringBuilder result = new StringBuilder();
        positionWithoutBonds.forEach(position -> result.append(msgData.getInfoPosition(position)));
        return result.toString();
    }

    public String getDiagramSectorData(Portfolio portfolio) {
        List<Position> allPositions = portfolio.getPositions();
        List<Position> positionWithoutBonds = allPositions.stream().filter(position -> position.getInstrumentType().equals("share")).toList();
        return msgData.getSectorData(positionWithoutBonds);
    }

    public String getReportPeriod(List<Operation> reportResponse) {
        return msgData.getReportPeriod(reportResponse);
    }

    @Scheduled(cron = "0 0 22 L * ?")
    public void getAutoMonthEndReport() {

    }

    //ToDo сделать отчет в конце дня + данные по Индексу
    //ToDo сделать отчет в конце недели + данные по Индексу
    //ToDo сделать отчет в конце месяца + данные по Индексу
    //ToDo сделать отчет в конце г/г + данные по Индексу

    //ToDo с НГ сделать метод по доходности (годовой и вообще)
}
