package com.investinfo.capital.telegram.msgsender;

import com.investinfo.capital.dto.MessageResponse;
import com.investinfo.capital.usecase.message.ServiceMessagesInitiatedClient;
import com.investinfo.capital.telegram.config.ValidationPerson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class MainMessageService extends MessageService {

    //ToDo сайт с кодами смайлов тут https://emojipedia.org/smiling-face-with-smiling-eyes
    //ToDo вынести все сообщения и смайлы в отдельный класс констант или только смайлы?

    private final ValidationPerson validationPerson;
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
        MessageResponse messageResponse;
        switch (text[0]) {
            case "/amount" -> messageResponse = messagesInitiatedClient.getPortfolioOverview();
            case "/position" -> messageResponse = messagesInitiatedClient.getPositionsWithoutBonds();
            case "/diagram_sec" -> messageResponse = messagesInitiatedClient.getDiagramSecuritiesSector();
            case "/report" -> messageResponse = messagesInitiatedClient.getReportForPeriod(getInstant(text[1]), getInstant(text[2]));
            case "день" -> messageResponse = messagesInitiatedClient.getDayOfYear();
            case "/help" -> messageResponse = messagesInitiatedClient.getAllCommand();
            default -> messageResponse = messagesInitiatedClient.getAllCommand();
        }
        return messageResponse;
    }

    private static Instant getInstant(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long ts;
        try {
            ts = dateFormat.parse(date).getTime() / 1000;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return Instant.ofEpochSecond(ts);
    }
}
