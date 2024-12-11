package com.investinfo.capital.usecase.message;

import com.investinfo.capital.config.EnvironmentParam;
import com.investinfo.capital.dto.MessageResponse;
import com.investinfo.capital.usecase.OperationReport;
import com.investinfo.capital.usecase.PortfolioReport;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.core.InvestApi;

import java.time.Instant;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

@Service
public class ServiceMessagesInitiatedClient extends ServiceMessage {

    private final EnvironmentParam environmentParam;
    private final PortfolioReport portfolioReport;
    private final OperationReport operationReport;

    public ServiceMessagesInitiatedClient(InvestApi investApi, EnvironmentParam environmentParam, EnvironmentParam environmentParam1, PortfolioReport portfolioReport, OperationReport operationReport) {
        super(investApi, environmentParam);
        this.environmentParam = environmentParam1;
        this.portfolioReport = portfolioReport;
        this.operationReport = operationReport;
    }

    public MessageResponse getPortfolioOverview() throws ExecutionException, InterruptedException {
        return new MessageResponse(
                portfolioReport.getPortfolioOverview(
                        getOperationsService()
                                .getPortfolio(environmentParam.tAccountId())
                                .get()));
    }

    public MessageResponse getPositionsWithoutBonds() throws ExecutionException, InterruptedException {
        return new MessageResponse(portfolioReport.getPositionsWithoutBonds(
                getOperationsService()
                        .getPortfolio(environmentParam.tAccountId())
                        .get()));
    }

    public MessageResponse getDiagramSecuritiesSector() throws ExecutionException, InterruptedException {
        return new MessageResponse(portfolioReport.getDiagramSecuritiesSector(
                getOperationsService()
                        .getPortfolio(environmentParam.tAccountId())
                        .get()));
    }

    public MessageResponse getReportForPeriod(Instant from, Instant to) throws ExecutionException, InterruptedException {
        return new MessageResponse(operationReport.getReportForPeriod(
                getOperationsService()
                        .getAllOperations(environmentParam.tAccountId(), from, to)
                        .get()));
    }

    public MessageResponse getDayOfYear() {
        return new MessageResponse(String.format("Сегодня %d день %d года",
                LocalDate.now().getDayOfYear(), LocalDate.now().getYear()));
    }

    public MessageResponse getAllCommand() {
        return new MessageResponse("""
                /amount - Общая статистика портфеля
                /position - Позиции без облигаций (цены и доходность)
                день - Показать номер дня в году
                /diagram_sec - соотношение секторов
                /report - отчет за период
                """);
    }
}
