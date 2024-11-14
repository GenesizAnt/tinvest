package com.investinfo.capital.telegram;

import com.investinfo.capital.controller.portfolio.PortfolioController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class MainMessageService {

    //ToDo сайт с кодами смайлов тут https://emojipedia.org/smiling-face-with-smiling-eyes
    //ToDo вынести все сообщения и смайлы в отдельный класс констант или только смайлы?

    private final ValidationPerson validationPerson;
    private final PortfolioController portfolioController;

    public SendMessage messageReceiver(Update update) throws ExecutionException, InterruptedException {
        if (validationPerson.isValid(update)) {
            String[] text = update.getMessage().getText().split(" ");
            Long chatId = update.getMessage().getChatId();
            String response = getResponse(text);
            return getReceiveMessage(chatId, response);
        }
        return getReceiveMessage(update.getMessage().getChatId(), "Извините, Вам нельзя отвечать, покиньте данный бот!");
    }

    private String getResponse(String[] text) throws ExecutionException, InterruptedException {
        String response;
        switch (text[0]) {
            case "/amount" -> response = portfolioController.getPortfolio();
            case "/position" -> response = portfolioController.getPositionWithoutBonds();
            case "день" -> response = getDayOfYear();
            case "/help" -> response = getAllCommand();
            //ToDo сделать команду замены тикера и пр.
            default -> {
                response = getAllCommand();
            }
        }
        return response;
    }

    private String getAllCommand() {
        return """
                /amount - Общая статистика портфеля
                /position - Позиции без облигаций
                день - Показать номер дня в году
                """;
    }

    private String getDayOfYear() {
        return String.format("Сегодня %d день %d года", LocalDate.now().getDayOfYear(), LocalDate.now().getYear());
    }

    private SendMessage getReceiveMessage(Long chatId, String response) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(response);
        return sendMessage;
    }
}
