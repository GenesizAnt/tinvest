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
}
