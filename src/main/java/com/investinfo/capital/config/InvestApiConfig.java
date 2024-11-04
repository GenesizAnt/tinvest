package com.investinfo.capital.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

@Configuration
public class InvestApiConfig {

    @Value("token")
    private String token;
//    private final AuthInterceptor authInterceptor = new AuthInterceptor(token);;

    public InvestApiConfig() {

    }

//    public AuthInterceptor getAuthInterceptor() {
//        return authInterceptor;
//    }

//    public static InvestApi init() {
//        return InvestApi.createReadonly(token);
//    }
}
