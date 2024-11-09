package com.investinfo.capital.controller.portfolio;

import com.investinfo.capital.config.EnigmaMachine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Controller
public class PortfolioController {

    private final InvestApi investApi;
    private final EnigmaMachine enigma;
    private final Map<String, String> userEnvironment;
    private final PortfolioData portfolioData;

    public String getPortfolio() throws ExecutionException, InterruptedException {
        return portfolioData.getPortfolio(investApi.getOperationsService().getPortfolio(userEnvironment.get("C_PORT")).get());
    }
}
