package com.investinfo.capital.controller.portfolio;

import com.investinfo.capital.dto.PositionDTO;
import com.investinfo.capital.service.ImoexPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

import java.util.List;

import static com.investinfo.capital.controller.FormatUtils.formantNumber;
import static com.investinfo.capital.controller.MathUtils.getPercentage;

@Component
@RequiredArgsConstructor
public class PortfolioData {

    private final ImoexPositionService positionService;

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


    //ToDo с НГ сделать метод по доходности (годовой и вообще)
}
