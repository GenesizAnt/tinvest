package com.investinfo.capital.telegram;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.telegram.telegrambots.meta.api.objects.Update;

@ConfigurationProperties(prefix = "valid.person.data")
public record ValidationPerson(Long user, String firstName, String lastName, Boolean isBot, String userName) {

    public boolean isValid(Update update) {
        return update.getMessage().getFrom().getId().equals(user)
                && update.getMessage().getFrom().getFirstName().equals(firstName)
                && update.getMessage().getFrom().getLastName().equals(lastName)
                && update.getMessage().getFrom().getIsBot() == isBot
                && update.getMessage().getFrom().getUserName().equals(userName);}
}
