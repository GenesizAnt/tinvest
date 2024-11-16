package com.investinfo.capital.controller.portfolio;

import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.tinkoff.piapi.contract.v1.BrokerReportResponse;
import ru.tinkoff.piapi.contract.v1.GetBrokerReportResponse;
import ru.tinkoff.piapi.core.InvestApi;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
        return portfolioData.getPositionWithoutBonds(investApi.getOperationsService().getPortfolio(userEnvironment.get("C_PORT")).get());
    }

    public String getDiagramSectorData() throws ExecutionException, InterruptedException {

        // Создаем два экземпляра LocalDate
        LocalDate date1 = LocalDate.of(2024, 11, 1);
        LocalDate date2 = LocalDate.now();

        // Преобразуем LocalDate в Instant
        Instant instant1 = date1.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant instant2 = date2.atStartOfDay(ZoneId.systemDefault()).toInstant();

        // Создаем Timestamp на основе Instant
        Timestamp timestamp1 = Timestamp.newBuilder()
                .setSeconds(instant1.getEpochSecond())
                .setNanos(instant1.getNano())
                .build();

        Timestamp timestamp2 = Timestamp.newBuilder()
                .setSeconds(instant2.getEpochSecond())
                .setNanos(instant2.getNano())
                .build();


        BrokerReportResponse cPort = investApi.getOperationsService().getBrokerReport(userEnvironment.get("C_PORT"), instant1, instant2).get();
        System.out.println();


        return portfolioData.getDiagramSectorData(investApi.getOperationsService().getPortfolio(userEnvironment.get("C_PORT")).get());
    }


    public String getReportPeriod() throws ExecutionException, InterruptedException {

        // Создаем два экземпляра LocalDate
        LocalDate date1 = LocalDate.of(2024, 11, 1);
        LocalDate date2 = LocalDate.now();

        // Преобразуем LocalDate в Instant
        Instant instant1 = date1.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant instant2 = date2.atStartOfDay(ZoneId.systemDefault()).toInstant();

        // Создаем Timestamp на основе Instant
        Timestamp timestamp1 = Timestamp.newBuilder()
                .setSeconds(instant1.getEpochSecond())
                .setNanos(instant1.getNano())
                .build();

        Timestamp timestamp2 = Timestamp.newBuilder()
                .setSeconds(instant2.getEpochSecond())
                .setNanos(instant2.getNano())
                .build();


        BrokerReportResponse cPort = investApi.getOperationsService().getBrokerReport(userEnvironment.get("C_PORT"), instant1, instant2).get();


        return "";
    }

}


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

