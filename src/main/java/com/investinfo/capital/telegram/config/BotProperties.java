package com.investinfo.capital.telegram.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
public class BotProperties {
    private final String name;
    private final String token;
    private final Map<String, String> userEnvironment;

    public BotProperties(Map<String, String> userEnvironment) {
        this.name = userEnvironment.get("C_TG_NAME");
        this.token = userEnvironment.get("C_TG_TOKEN");
        this.userEnvironment = userEnvironment;
    }
}