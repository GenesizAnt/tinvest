package com.investinfo.capital.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class EnvironmentParam {

    private final Map<String, String> userEnvironment;

    public String tAccountId() {
        return userEnvironment.get("C_PORT");
    }

    public Long tgChatId() {
        return Long.valueOf(userEnvironment.get("C_USER"));
    }

    public String tgNameBot() {
        return userEnvironment.get("C_TG_NAME");
    }

    public String tgToken() {
        return userEnvironment.get("C_TG_TOKEN");
    }

    public String firstName() {
        return userEnvironment.get("C_F_NAME");
    }

    public String lastName() {
        return userEnvironment.get("C_L_NAME");
    }

    public String tgUserName() {
        return userEnvironment.get("C_USERNAME");
    }

    public Boolean isBot() {
        return Boolean.valueOf(userEnvironment.get("C_IS_BOT"));
    }
}