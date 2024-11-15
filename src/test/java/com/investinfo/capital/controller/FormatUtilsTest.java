package com.investinfo.capital.controller;

import com.investinfo.capital.service.ImoexPositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatUtilsTest {

    private FormatUtils msgData;

    @BeforeEach
    public void setUp() {
        ImoexPositionService imoexPositionService = Mockito.mock(ImoexPositionService.class);
        msgData = new FormatUtils(imoexPositionService);
    }

    @Test
    public void testFormantNumberWithPositiveValue() {
        BigDecimal amount = new BigDecimal("1234567.89");
        String expected = "1 234 567.89";
        String actual = msgData.formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithNegativeValue() {
        BigDecimal amount = new BigDecimal("-1234567.89");
        String expected = "-1 234 567.89";
        String actual = msgData.formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithZeroValue() {
        BigDecimal amount = new BigDecimal("0.00");
        String expected = "0.00";
        String actual = msgData.formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithLargeValue() {
        BigDecimal amount = new BigDecimal("1234567890123456789.99");
        String expected = "1 234 567 890 123 456 789.99";
        String actual = msgData.formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithSmallValue() {
        BigDecimal amount = new BigDecimal("0.123456789");
        String expected = "0.12";
        String actual = msgData.formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }
}