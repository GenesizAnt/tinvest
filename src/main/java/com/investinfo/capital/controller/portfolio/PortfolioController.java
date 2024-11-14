package com.investinfo.capital.controller.portfolio;

import com.investinfo.capital.config.EnigmaMachine;
import com.investinfo.capital.dto.mapper.ShareMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Controller
public class PortfolioController {

    private final InvestApi investApi;
    private final Map<String, String> userEnvironment;
    private final PortfolioData portfolioData;

    public String getPortfolio() throws ExecutionException, InterruptedException {
        return portfolioData.getPortfolio(investApi.getOperationsService().getPortfolio(userEnvironment.get("C_PORT")).get());
    }

    public String getPositionWithoutBonds() throws ExecutionException, InterruptedException {

//        Etf tcs10A101X50 = investApi.getInstrumentsService().getEtfByFigi("TCS10A101X50").get();
//        System.out.println();
//        List<Share> my = shares.stream().filter(share ->
//                share.getCountryOfRisk().equals("RU")).toList();
//        List<ShareDTO> sss = new ArrayList<>();
//        my.forEach(share -> sss.add(shareMapper.toDto(share)));
//
//        sss.forEach(System.out::println);
//
//        System.out.println();


        return portfolioData.getPositionWithoutBonds(investApi.getOperationsService().getPortfolio(userEnvironment.get("C_PORT")).get());
    }
}
