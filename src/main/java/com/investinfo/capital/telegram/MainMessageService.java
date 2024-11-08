package com.investinfo.capital.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MainMessageService {

    //ToDo сайт с кодами смайлов тут https://emojipedia.org/smiling-face-with-smiling-eyes
    //ToDo вынести все сообщения и смайлы в отдельный класс констант или только смайлы?

    private final ValidationPerson validationPerson;

    //Получает и обрабатывает сообщение отправленное в бот
    public SendMessage messageReceiver(Update update) {

        if (validationPerson.isValid(update)) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String name = update.getMessage().getChat().getFirstName();
            Integer messageId = update.getMessage().getMessageId();


            String response;
            switch (text) {
                case "/start" -> {
//                    if (userTelegramService.isUserNotFoundById(chatId)) {
//                        registerUser(chatId, update.getMessage().getChat());
//                        response = EmojiParser.parseToUnicode(String.format("Привет %s!" +
//                                " Для использования всех функций бота необходимо ввести email, который был" +
//                                " использован в приложении \"Время профессионалов\" %s", name, ":blush:"));
//                    } else if (userTelegramService.isUserEmailEmpty(chatId)) {
//                        response = EmojiParser.parseToUnicode(String.format("Привет %s! Напоминаю, что для" +
//                                " использования всех функций бота необходимо ввести email, который был использован" +
//                                " в приложении \"Время профессионалов\" %s", name, ":blush:"));
//                    } else {
//                        response = EmojiParser.parseToUnicode(String.format("Привет %s! Чем могу помочь? %s", name, ":blush:"));
//                    }
                }
                case "/changepassword" -> {
//                    UserTelegram user = null;
//                    response = "";
//                    if (user == null) {
////                        response = EmojiParser.parseToUnicode(String.format("Вы еще не зарегистрированы! В меню нажмите /start %s", ":blush:"));
//                    } else {
//                        if (user.isAgree()) {
//                            response = EmojiParser.parseToUnicode(String.format("%s, чтобы изменить пароль в новом сообщении" +
//                                    " укажите \"Пароль (пароль, который хотите установить)\" %s", name, ":blush:"));
//                        }
//                        if (!user.isAgree()) {
//                            response = EmojiParser.parseToUnicode(String.format("%s, Ваше подключение к боту еще не подтверждено. " +
//                                    "Для подтверждения перейдите на страницу \"Мой профиль\" в основном приложении  %s", name, ":blush:"));
//                        }
//                    }
                }
                default -> {

                }
            }
            return getReceiveMessage(chatId, "response");
        }
        return getReceiveMessage(update.getMessage().getChatId(), "Извините, Вам нельзя отвечать, покиньте данный бот!");
    }

    //Обработка разных сценариев неизвестного сообщения от пользователя
//    private String getResponse(String text, Long chatId, String name, Integer messageId) {
//        String response;
//        if (text.matches(regex)) {
//            Optional<Person> personByEmail = personService.findByEmail(text.toLowerCase());
//            if (personByEmail.isPresent()) {
//                if (userTelegramService.isUserEmailEmpty(chatId)) {
//                    //Получение email из текста сообщения и обновление ТГ пользователя
//                    UserTelegram userTelegram = userTelegramService.findById(chatId);
//                    userTelegram.setEmail(text);
//                    Person person = personByEmail.get();
//                    userTelegram.setPersonMainService(person);
//                    userTelegram.setRole(person.getRole());
//                    userTelegramService.save(userTelegram);
//                    response = "Email успешно добавлен, теперь доступны уведомления";
//                } else {
//                    response = String.format("%s, email уже был добавлен!", name);
//                }
//            } else {
//                response = "Вижу, что вы указали email, при этом не удалось найти зарегистрированного " +
//                        "пользователя в приложении \"Время профессионалов\". Ссылку на регистрацию можете" +
//                        " попросить у вашего специалиста";
//            }
//        } else if (text.startsWith("Пароль")) {
//            personService.setNewPassword(text, userTelegramService.findById(chatId).getPersonMainService());
//            response = "Пароль изменен и зашифрован" + messageId;
//        } else {
//            response = "Не понимать тебя";
//        }
//        return response;
//    }
//
//    // регистрация пользователя телеграм
//    private void registerUser(Long chatId, Chat chat) {
//        if (userTelegramService.isUserNotFoundById(chatId)) {
//            UserTelegram userTelegram = new UserTelegram();
//            userTelegram.setChatId(chatId);
//            userTelegram.setFirstName(chat.getFirstName());
//            userTelegram.setLastName(chat.getLastName());
//            userTelegram.setPersonusername(chat.getUserName());
//            userTelegramService.save(userTelegram);
//        }
//    }

    //Формируем объект, который содержит ответ увидит клиент
    private SendMessage getReceiveMessage(Long chatId, String response) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(response);
        return sendMessage;
    }

    private SendMessage getReceiveMessage(Long chatId, String response, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(response);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}
