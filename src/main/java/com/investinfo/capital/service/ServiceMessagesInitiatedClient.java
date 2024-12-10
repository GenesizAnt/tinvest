package com.investinfo.capital.service;

import com.investinfo.capital.config.EnvironmentParam;
import com.investinfo.capital.entity.MessageResponse;
import com.investinfo.capital.usecase.PortfolioReport;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.concurrent.ExecutionException;

@Service
public class ServiceMessagesInitiatedClient extends ServiceMessage {

    private final EnvironmentParam environmentParam;
    private final PortfolioReport portfolioReport;

    public ServiceMessagesInitiatedClient(InvestApi investApi, EnvironmentParam environmentParam, EnvironmentParam environmentParam1, PortfolioReport portfolioReport) {
        super(investApi, environmentParam);
        this.environmentParam = environmentParam1;
        this.portfolioReport = portfolioReport;
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


    //        return portfolioData.getPortfolio(investApi.getOperationsService().getPortfolio(userEnvironment.get("C_PORT")).get());
}
