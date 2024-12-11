package com.investinfo.capital.usecase.message;

import com.investinfo.capital.config.EnvironmentParam;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.piapi.core.*;

@RequiredArgsConstructor
public abstract class ServiceMessage {

    private final InvestApi investApi;
    private final EnvironmentParam environmentParam;

    protected String tAccountId() {
        return environmentParam.tAccountId();
    }

    protected Long tgChatId() {
        return environmentParam.tgChatId();
    }

    protected UsersService getUsersService() {
        return investApi.getUserService();
    }

    protected MarketDataService getMarketDataService() {
        return investApi.getMarketDataService();
    }

    protected OperationsService getOperationsService() {
        return investApi.getOperationsService();
    }

    protected InstrumentsService getInstrumentsService() {
        return investApi.getInstrumentsService();
    }
}
