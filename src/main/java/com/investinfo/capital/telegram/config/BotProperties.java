package com.investinfo.capital.telegram.config;

import com.investinfo.capital.config.EnvironmentParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BotProperties {
    private final String name;
    private final String token;
    private final EnvironmentParam environmentParam;

    public BotProperties(EnvironmentParam environmentParam) {
        this.environmentParam = environmentParam;
        this.name = environmentParam.tgNameBot();
        this.token = environmentParam.tgToken();
    }
}