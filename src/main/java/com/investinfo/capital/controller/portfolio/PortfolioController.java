package com.investinfo.capital.controller.portfolio;

import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.tinkoff.piapi.contract.v1.BrokerReportResponse;
import ru.tinkoff.piapi.contract.v1.GetOperationsByCursorResponse;
import ru.tinkoff.piapi.contract.v1.Operation;
import ru.tinkoff.piapi.core.InvestApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.aspectj.bridge.Version.getTime;

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
        return portfolioData.getDiagramSectorData(investApi.getOperationsService().getPortfolio(userEnvironment.get("C_PORT")).get());
    }

    public String getReportPeriod(String from, String to) throws ExecutionException, InterruptedException {

        GetOperationsByCursorResponse cPort = investApi.getOperationsService().getOperationByCursor(userEnvironment.get("C_PORT"), getInstant(from), getInstant(to)).get();
        List<Operation> cPort1 = investApi.getOperationsService().getAllOperations(userEnvironment.get("C_PORT"), getInstant(from), getInstant(to)).get();
        System.out.println();

//        // Создаем два экземпляра LocalDate
//        LocalDate date1 = LocalDate.of(2024, 11, 1);
//        LocalDate date2 = LocalDate.now();
//        LocalDate parse = LocalDate.parse(from);
//
//        // Преобразуем LocalDate в Instant

//        Instant perFrom = getInstant(from);
//        Instant perTo = getInstant(to);


//        BrokerReportResponse cPort = investApi.getOperationsService().getBrokerReport(userEnvironment.get("C_PORT"), instant1, instant2).get();

        return portfolioData.getReportPeriod(investApi.getOperationsService().getBrokerReport(userEnvironment.get("C_PORT"), getInstant(from), getInstant(to)).get());
    }

    private static Instant getInstant(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long ts = 0;
        try {
            ts = dateFormat.parse(date).getTime() / 1000;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Instant instant = Instant.ofEpochSecond(ts);
        System.out.println();
        return Instant.ofEpochSecond(ts);
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

