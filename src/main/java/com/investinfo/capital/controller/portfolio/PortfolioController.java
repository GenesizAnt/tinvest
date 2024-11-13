package com.investinfo.capital.controller.portfolio;

import com.investinfo.capital.config.EnigmaMachine;
import com.investinfo.capital.dto.ShareDTO;
import com.investinfo.capital.dto.mapper.ShareMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.tinkoff.piapi.contract.v1.InstrumentStatus;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Controller
public class PortfolioController {

    private final InvestApi investApi;
    private final EnigmaMachine enigma;
    private final Map<String, String> userEnvironment;
    private final PortfolioData portfolioData;
    private final ShareMapper shareMapper;

    public String getPortfolio() throws ExecutionException, InterruptedException {
        return portfolioData.getPortfolio(investApi.getOperationsService().getPortfolio(userEnvironment.get("C_PORT")).get());
    }

    public String getPositionWithoutBonds() throws ExecutionException, InterruptedException {

        List<Share> shares = investApi.getInstrumentsService().getShares(InstrumentStatus.INSTRUMENT_STATUS_BASE).get();
        List<Share> my = shares.stream().filter(share ->
                share.getCountryOfRisk().equals("RU")).toList();
        List<ShareDTO> sss = new ArrayList<>();
        my.forEach(share -> sss.add(shareMapper.toDto(share)));
        System.out.println();


        return portfolioData.getPositionWithoutBonds(investApi.getOperationsService().getPortfolio(userEnvironment.get("C_PORT")).get());
    }
}
