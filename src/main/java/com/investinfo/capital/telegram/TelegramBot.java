package com.investinfo.capital.telegram;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBot extends TelegramLongPollingBot  {

    private final BotProperties botProperties;
    private final MainMessageService mainMessageService;

    @Autowired
    public TelegramBot(BotProperties botProperties, MainMessageService mainMessageService) {
        super(botProperties.getToken());
        this.botProperties = botProperties;
        this.mainMessageService = mainMessageService;
        try {
            this.execute(new SetMyCommands(listMenuCommand(), new BotCommandScopeDefault(),null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = mainMessageService.messageReceiver(update);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    private List<BotCommand> listMenuCommand() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("amount", "Общая статистика портфеля"));
        listOfCommands.add(new BotCommand("position", "Позиции без облигаций"));
        return listOfCommands;
    }
}
