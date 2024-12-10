package com.investinfo.capital.telegram.msgsender;

import com.investinfo.capital.service.SnapshotPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class ScheduledMessageService {

    private final SnapshotPositionService snapshotPositionService;
    private final InvestApi investApi;
    private final Map<String, String> userEnvironment;

    public SendMessage getEveryDayEndReport() {
        try {
            return getReceiveMessage(
                    Long.valueOf(userEnvironment.get("C_USER")),
                    snapshotPositionService.getEveryDayEndReport(
                            investApi.getOperationsService().getPortfolio(
                                    userEnvironment.get("C_PORT")).get()));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    //ToDO дублирующийся код
    private SendMessage getReceiveMessage(Long chatId, String response) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(response);
        return sendMessage;
    }
}
