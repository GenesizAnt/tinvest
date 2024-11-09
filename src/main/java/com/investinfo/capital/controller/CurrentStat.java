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

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
public class CurrentStat {

//    @Value("${token}")
//    private static String token;

    @Value("${tin.api}")
    private static String token;


    public void getStat() {
        String url = "";
        System.out.println(token);
//        InvestApi investApi = InvestApiConfig.init();
//        UsersService userService = investApi.getUserService();
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
//        String responseBody = response.getBody();
//        System.out.println(responseBody);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidParameterSpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {


        String password1 = token;

        String password = "d";
        String salt = "a";

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        System.out.println(secret);

        /* Encrypt the message. */
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] ciphertext = cipher.doFinal("1231.15".getBytes(StandardCharsets.UTF_8));
        System.out.println(ciphertext);

        /* Decrypt the message, given derived key and initialization vector. */
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
        String plaintext = new String(cipher.doFinal(ciphertext), StandardCharsets.UTF_8);
        System.out.println(plaintext);

        System.out.println();

//        InvestApi readonlyToken = InvestApi.createReadonly(f);

//        CompletableFuture<Portfolio> portfolio = readonlyToken.getOperationsService().getPortfolio("");
//        Portfolio portfolio1 = portfolio.get();
//        System.out.println(portfolio1);







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
