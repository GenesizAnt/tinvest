package com.investinfo.capital.telegram.config;

import com.investinfo.capital.config.EnvironmentParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ValidationPersonTest {

    private ValidationPerson validationPerson;

    @BeforeEach
    public void setUp() {
        EnvironmentParam environmentParam = Mockito.mock(EnvironmentParam.class);
        when(environmentParam.tgChatId()).thenReturn(12345L);
        when(environmentParam.firstName()).thenReturn("John");
        when(environmentParam.lastName()).thenReturn("Doe");
        when(environmentParam.isBot()).thenReturn(false);
        when(environmentParam.tgUserName()).thenReturn("johndoe");

        validationPerson = new ValidationPerson(environmentParam);
    }

    @Test
    public void testIsValid_ValidUpdate_ReturnsTrue() {
        // Создаем реальные объекты Update, Message и User
        User user = new User();
        user.setId(12345L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setIsBot(false);
        user.setUserName("johndoe");

        Message message = new Message();
        message.setFrom(user);

        Update update = new Update();
        update.setMessage(message);

        // Проверяем, что метод isValid возвращает true
        assertTrue(validationPerson.isValid(update));
    }

    @Test
    public void testIsValid_InvalidUpdate_ReturnsFalse() {
        // Создаем реальные объекты Update, Message и User с некорректными данными
        User user = new User();
        user.setId(67890L); // Некорректный ID
        user.setFirstName("Jane"); // Некорректное имя
        user.setLastName("Smith"); // Некорректная фамилия
        user.setIsBot(true); // Некорректный статус бота
        user.setUserName("janesmith"); // Некорректное имя пользователя

        Message message = new Message();
        message.setFrom(user);

        Update update = new Update();
        update.setMessage(message);

        // Проверяем, что метод isValid возвращает false
        assertFalse(validationPerson.isValid(update));
    }
}