package com.investinfo.capital.controller;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FormatUtilsTest {

    @Test
    public void testFormantNumberWithPositiveValue() {
        BigDecimal amount = new BigDecimal("1234567.89");
        String expected = "1 234 567.89";
        String actual = FormatUtils.formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithNegativeValue() {
        BigDecimal amount = new BigDecimal("-1234567.89");
        String expected = "-1 234 567.89";
        String actual = FormatUtils.formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithZeroValue() {
        BigDecimal amount = new BigDecimal("0.00");
        String expected = "0.00";
        String actual = FormatUtils.formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithLargeValue() {
        BigDecimal amount = new BigDecimal("1234567890123456789.99");
        String expected = "1 234 567 890 123 456 789.99";
        String actual = FormatUtils.formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testFormantNumberWithSmallValue() {
        BigDecimal amount = new BigDecimal("0.123456789");
        String expected = "0.12";
        String actual = FormatUtils.formantNumber(amount).replace('\u00A0', ' ');
        assertEquals(expected, actual);
    }
}