package com.investinfo.capital.telegram.msgsender;

import com.investinfo.capital.dto.MessageResponse;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class MessageService {

    protected SendMessage getReceiveMessage(Long chatId, MessageResponse response) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(response.message());
        return sendMessage;
    }
}
