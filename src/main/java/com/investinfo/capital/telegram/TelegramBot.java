package com.investinfo.capital.telegram;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    private final ScheduledMessageService scheduledMessageService;

    @Autowired
    public TelegramBot(BotProperties botProperties, MainMessageService mainMessageService, ScheduledMessageService scheduledMessageService) {
        super(botProperties.getToken());
        this.botProperties = botProperties;
        this.mainMessageService = mainMessageService;
        this.scheduledMessageService = scheduledMessageService;
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

    //Todo метод раз в месяц сделать
    @Scheduled(cron = "0 0 22 * * ?")
    public void getAutoDayEndReport() {

    }

    @Scheduled(cron = "*/5 * * * * *")
    public void getTestEveryDayEndReport() {
        SendMessage sendMessage = scheduledMessageService.getEveryDayEndReport();
    }

//    public void notifySendler() {
//        List<SendMessage> messageList = notifyMessageService.messageReceiver();
//        try {
//            for (SendMessage sendMessage : messageList) {
//                execute(sendMessage);
//            }
//        } catch (TelegramApiException e) {
//            log.error("Ошибка отправки сообщений по расписанию: " + messageList + ". Текст ошибки: " + e.getMessage());
//        }
//    }

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
        listOfCommands.add(new BotCommand("diagram_sec", "Доля секторов в портфеле"));
        listOfCommands.add(new BotCommand("report", "Отчет за период"));
        listOfCommands.add(new BotCommand("help", "Список команд"));
        return listOfCommands;
    }
}
