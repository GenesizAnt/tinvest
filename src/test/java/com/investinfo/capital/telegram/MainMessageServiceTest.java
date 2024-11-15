package com.investinfo.capital.telegram;

import com.investinfo.capital.controller.portfolio.PortfolioController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MainMessageServiceTest {

    private MainMessageService messageService;

    @BeforeEach
    public void setUp() {
        ValidationPerson validationPerson = Mockito.mock(ValidationPerson.class);
        PortfolioController portfolioController = Mockito.mock(PortfolioController.class);
        PrepareResponse prepareResponse = Mockito.mock(PrepareResponse.class);
        messageService = new MainMessageService(validationPerson, portfolioController, prepareResponse);
    }

    @Test
    public void testGetDayOfYear() throws Exception {
        LocalDate mockedDate = LocalDate.of(2023, 10, 10);
        try (MockedStatic<LocalDate> mockedLocalDate = Mockito.mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(mockedDate);

            String expected = String.format("Сегодня %d день %d года", mockedDate.getDayOfYear(), mockedDate.getYear());
            String actual = invokePrivateMethod("getDayOfYear");

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGetDayOfYearLeapYear() throws Exception {
        LocalDate mockedDate = LocalDate.of(2020, 2, 29);
        try (MockedStatic<LocalDate> mockedLocalDate = Mockito.mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(mockedDate);

            String expected = String.format("Сегодня %d день %d года", mockedDate.getDayOfYear(), mockedDate.getYear());
            String actual = invokePrivateMethod("getDayOfYear");

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGetDayOfYearFirstDayOfYear() throws Exception {
        LocalDate mockedDate = LocalDate.of(2023, 1, 1);
        try (MockedStatic<LocalDate> mockedLocalDate = Mockito.mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(mockedDate);

            String expected = String.format("Сегодня %d день %d года", mockedDate.getDayOfYear(), mockedDate.getYear());
            String actual = invokePrivateMethod("getDayOfYear");

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGetDayOfYearLastDayOfYear() throws Exception {
        LocalDate mockedDate = LocalDate.of(2023, 12, 31);
        try (MockedStatic<LocalDate> mockedLocalDate = Mockito.mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(mockedDate);

            String expected = String.format("Сегодня %d день %d года", mockedDate.getDayOfYear(), mockedDate.getYear());
            String actual = invokePrivateMethod("getDayOfYear");

            assertEquals(expected, actual);
        }
    }

    private String invokePrivateMethod(String methodName) throws Exception {
        Method method = MainMessageService.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return (String) method.invoke(messageService);
    }
}