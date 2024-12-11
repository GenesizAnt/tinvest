package com.investinfo.capital.telegram.msgsender;

import com.investinfo.capital.config.EnvironmentParam;
import com.investinfo.capital.usecase.message.ServiceMessagesInitiatedApp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class ScheduledMessageService extends MessageService {

    private final EnvironmentParam environmentParam;
    private final ServiceMessagesInitiatedApp messagesInitiatedApp;

    public SendMessage getEveryDayEndReport() {
        try {
            return getReceiveMessage(environmentParam.tgChatId(), messagesInitiatedApp.getEveryDayEndReport());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
