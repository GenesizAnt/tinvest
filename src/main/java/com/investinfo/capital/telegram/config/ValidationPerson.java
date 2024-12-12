package com.investinfo.capital.telegram.config;

import com.investinfo.capital.config.EnvironmentParam;
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
    private final EnvironmentParam environmentParam;

    public ValidationPerson(EnvironmentParam environmentParam) {
        this.environmentParam = environmentParam;
        this.user = environmentParam.tgChatId();
        this.firstName = environmentParam.firstName();
        this.lastName = environmentParam.lastName();
        this.isBot = environmentParam.isBot();
        this.userName = environmentParam.tgUserName();
    }

    public boolean isValid(Update update) {
        return update.getMessage().getFrom().getId().equals(user)
                && update.getMessage().getFrom().getFirstName().equals(firstName)
                && update.getMessage().getFrom().getLastName().equals(lastName)
                && update.getMessage().getFrom().getIsBot() == isBot
                && update.getMessage().getFrom().getUserName().equals(userName);
    }
}
