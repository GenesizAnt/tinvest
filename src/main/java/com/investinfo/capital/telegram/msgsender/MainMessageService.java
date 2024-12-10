package com.investinfo.capital.telegram.msgsender;

import com.investinfo.capital.controller.portfolio.PortfolioController;
import com.investinfo.capital.entity.MessageResponse;
import com.investinfo.capital.service.ServiceMessagesInitiatedClient;
import com.investinfo.capital.telegram.config.ValidationPerson;
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
    private final PrepareResponse prepareResponse;
    private final ServiceMessagesInitiatedClient messagesInitiatedClient;

    public SendMessage messageReceiver(Update update) throws ExecutionException, InterruptedException {
        if (validationPerson.isValid(update)) {
            String[] text = update.getMessage().getText().split(" ");
            Long chatId = update.getMessage().getChatId();
            MessageResponse response = getResponse(text);
            return getReceiveMessage(chatId, response);
        }
        return getReceiveMessage(update.getMessage().getChatId(), new MessageResponse("Извините, Вам нельзя отвечать, покиньте данный бот!"));
    }

    private MessageResponse getResponse(String[] text) throws ExecutionException, InterruptedException {
        String response;
        MessageResponse messageResponse = null;
        switch (text[0]) {
            case "/amount" -> messageResponse = messagesInitiatedClient.getPortfolioOverview();
            case "/position" -> messageResponse = messagesInitiatedClient.getPositionsWithoutBonds();
//            case "/position" -> response = portfolioController.getPositionWithoutBonds();
            case "день" -> response = getDayOfYear();
            case "/diagram_sec" -> response = portfolioController.getDiagramSectorData();
            case "/report" -> response = portfolioController.getReportPeriod(text[1], text[2]);
            case "/help" -> response = getAllCommand();
            //ToDo сделать команду замены тикера и пр.
            default -> response = getAllCommand();
        }
        return messageResponse;
    }

    private SendMessage getReceiveMessage(Long chatId, MessageResponse response) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(response.message());
        return sendMessage;
    }

    private String getAllCommand() {
        return """
                /amount - Общая статистика портфеля
                /position - Позиции без облигаций (цены и доходность)
                день - Показать номер дня в году
                /diagram_sec - соотношение секторов
                /report - отчет за период
                """;
    }

    private String getDayOfYear() {
        return String.format("Сегодня %d день %d года", LocalDate.now().getDayOfYear(), LocalDate.now().getYear());
    }
}
