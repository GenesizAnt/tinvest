package com.investinfo.capital.telegram.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Getter
@Setter
@Component
public class ValidationPerson {

    private final Long user;
    private final String firstName;
    private final String lastName;
    private final Boolean isBot;
    private final String userName;
    private final Map<String, String> userEnvironment;

    public ValidationPerson(Map<String, String> userEnvironment) {
        this.user = Long.valueOf(userEnvironment.get("C_USER"));
        this.firstName = userEnvironment.get("C_F_NAME");
        this.lastName = userEnvironment.get("C_L_NAME");
        this.isBot = Boolean.valueOf(userEnvironment.get("C_IS_BOT"));
        this.userName = userEnvironment.get("C_USERNAME");
        this.userEnvironment = userEnvironment;
    }

    public boolean isValid(Update update) {
        return update.getMessage().getFrom().getId().equals(user)
                && update.getMessage().getFrom().getFirstName().equals(firstName)
                && update.getMessage().getFrom().getLastName().equals(lastName)
                && update.getMessage().getFrom().getIsBot() == isBot
                && update.getMessage().getFrom().getUserName().equals(userName);}
}
