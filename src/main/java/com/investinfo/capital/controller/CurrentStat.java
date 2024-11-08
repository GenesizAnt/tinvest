package com.investinfo.capital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.piapi.contract.v1.Account;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.models.Portfolio;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
public class CurrentStat {

    @Value("${token}")
    private static String token;


    public void getStat() {
        String url = "";
//        InvestApi investApi = InvestApiConfig.init();
//        UsersService userService = investApi.getUserService();
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
//        String responseBody = response.getBody();
//        System.out.println(responseBody);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {




        InvestApi readonlyToken = InvestApi.createReadonly(f);

        CompletableFuture<Portfolio> portfolio = readonlyToken.getOperationsService().getPortfolio("");
        Portfolio portfolio1 = portfolio.get();
        System.out.println(portfolio1);

//        MarketDataService marketDataService = readonlyToken.getMarketDataService();
//        CompletableFuture<List<LastPrice>> lastPrices = marketDataService.getLastPrices(Collections.singleton("BBG004730N88"));
//        List<LastPrice> lastPrices1 = lastPrices.get();
//        System.out.println();


        //Получить счет и его состав
//        OperationsService operationsService = readonlyToken.getOperationsService();
//        CompletableFuture<Portfolio> portfolio = operationsService.getPortfolio(readonlyToken.getUserService().getAccounts().get().getFirst().getId());
//        Portfolio portfolio1 = portfolio.get();
//        System.out.println();





//        var accounts = readonlyToken.getUserService().getAccountsSync();
//        var mainAccount = accounts.getFirst();

        //Запрашиваем отчет
//        var response = readonlyToken.getOperationsService().getBrokerReportSync(mainAccount.getId(), Instant.now().minus(30, ChronoUnit.DAYS), Instant.now());
//        if (response.hasGenerateBrokerReportResponse()) {
//            //Если отчет не готов - вернется task_id
//            System.out.println("task_id: {} " + response.getGenerateBrokerReportResponse().getTaskId());
//        } else if (response.hasGetBrokerReportResponse()) {
//            //Если отчет уже готов - вернется отчет
//            var report = response.getGetBrokerReportResponse();
//            System.out.println("отчет содержит в себе {} позиций " + report.getItemsCount());
//        }

//        readonlyToken.getUserService();
//        readonlyToken.getInstrumentsService();
//        readonlyToken.getMarketDataService();
//        readonlyToken.getMarketDataStreamService();
//        readonlyToken.getOperationsService();
//        readonlyToken.getOperationsStreamService();




//        List<Account> accounts1 = accounts.get();
//        accounts2.get().forEach(System.out::println);

//        CompletableFuture<List<Account>> accounts = readonlyToken.getSandboxService().getAccounts();
//
//        System.out.println();
//        Channel channel = readonlyToken.getChannel();

    }
}
