package com.investinfo.capital.telegram;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "telegram.bot")
public record BotProperties(String name, String token) {
}