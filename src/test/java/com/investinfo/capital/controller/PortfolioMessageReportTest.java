package com.investinfo.capital.controller;

import com.investinfo.capital.service.ImoexPositionService;
import com.investinfo.capital.usecase.PortfolioMessageReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static com.investinfo.capital.usecase.utils.FormatNumberUtils.formantNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PortfolioMessageReportTest {

    @Test
    public void testFormantNumberWithPositiveValue() {
        BigDecimal amount = new BigDecimal("1234567.89");
        String expected = "1 234 567.89";
        String actual = formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithNegativeValue() {
        BigDecimal amount = new BigDecimal("-1234567.89");
        String expected = "-1 234 567.89";
        String actual = formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithZeroValue() {
        BigDecimal amount = new BigDecimal("0.00");
        String expected = "0.00";
        String actual = formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithLargeValue() {
        BigDecimal amount = new BigDecimal("1234567890123456789.99");
        String expected = "1 234 567 890 123 456 789.99";
        String actual = formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithSmallValue() {
        BigDecimal amount = new BigDecimal("0.123456789");
        String expected = "0.12";
        String actual = formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }
}