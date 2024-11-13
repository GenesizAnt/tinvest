package com.investinfo.capital.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.Map;

@SpringBootApplication
@ComponentScan(basePackages = {"com.investinfo.capital"})
public class CapitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapitalApplication.class, args);
    }

    @Bean
    public Map<String, String> userEnvironment() {
        return System.getenv();
    }

    @Bean
    public InvestApi investApi() {
        return InvestApi.createReadonly(userEnvironment().get("C_TOKEN"));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
