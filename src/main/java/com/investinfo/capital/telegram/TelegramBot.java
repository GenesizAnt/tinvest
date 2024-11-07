package com.investinfo.capital.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final BotProperties botProperties;

    @Autowired
    public TelegramBot(BotProperties botProperties) {
        super(botProperties.token());
        this.botProperties = botProperties;
    }


    @Override
    public String getBotUsername() {
        return botProperties.name();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
