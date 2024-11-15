package com.investinfo.capital.telegram;

import com.investinfo.capital.controller.portfolio.PortfolioController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class MainMessageService {

    //ToDo сайт с кодами смайлов тут https://emojipedia.org/smiling-face-with-smiling-eyes
    //ToDo вынести все сообщения и смайлы в отдельный класс констант или только смайлы?

    private final ValidationPerson validationPerson;
    private final PortfolioController portfolioController;
    private final PrepareResponse prepareResponse;

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
            case "день" -> response = prepareResponse.getDayOfYear();
            case "/diagram_sec" -> response = portfolioController.getDiagramSectorData();
            case "/help" -> response = prepareResponse.getAllCommand();
            //ToDo сделать команду замены тикера и пр.
            default -> response = prepareResponse.getAllCommand();
        }
        return response;
    }

    private SendMessage getReceiveMessage(Long chatId, String response) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(response);
        return sendMessage;
    }
}
