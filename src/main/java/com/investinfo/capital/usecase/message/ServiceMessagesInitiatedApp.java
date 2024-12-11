package com.investinfo.capital.usecase.message;

import com.investinfo.capital.config.EnvironmentParam;
import com.investinfo.capital.dto.MessageResponse;
import com.investinfo.capital.usecase.PortfolioReport;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.concurrent.ExecutionException;

@Service
public class ServiceMessagesInitiatedApp extends ServiceMessage {

    private final EnvironmentParam environmentParam;
    private final PortfolioReport portfolioReport;

    public ServiceMessagesInitiatedApp(InvestApi investApi, EnvironmentParam environmentParam, EnvironmentParam environmentParam1, PortfolioReport portfolioReport) {
        super(investApi, environmentParam);
        this.environmentParam = environmentParam1;
        this.portfolioReport = portfolioReport;
    }

    public MessageResponse getEveryDayEndReport() throws ExecutionException, InterruptedException {
        return new MessageResponse(
                portfolioReport.getEveryDayEndReport(
                        getOperationsService()
                                .getPortfolio(environmentParam.tAccountId())
                                .get()));
    }
}