package com.investinfo.capital.service;

import com.investinfo.capital.config.EnvironmentParam;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import ru.tinkoff.piapi.core.*;

import java.util.Map;

@RequiredArgsConstructor
public abstract class ServiceMessage {

    private final InvestApi investApi;
    private final EnvironmentParam environmentParam;

    public String tAccountId() {
        return environmentParam.tAccountId();
    }

    public Long tgChatId() {
        return environmentParam.tgChatId();
    }

    public UsersService getUsersService() {
        return investApi.getUserService();
    }

    public MarketDataService getMarketDataService() {
        return investApi.getMarketDataService();
    }

    public OperationsService getOperationsService() {
        return investApi.getOperationsService();
    }

    public InstrumentsService getInstrumentsService() {
        return investApi.getInstrumentsService();
    }
}
