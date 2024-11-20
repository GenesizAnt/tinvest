package com.investinfo.capital;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.Map;

@SpringBootApplication
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

    // контейнер - https://www.youtube.com/playlist?list=PLs_aLxm3VDLureakxc1HNGeaT_H7wxUgd
}
