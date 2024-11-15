package com.investinfo.capital.controller;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.investinfo.capital.controller.MathUtils.getPercentage;
import static org.assertj.core.api.Assertions.assertThat;

class MathUtilsTest {

    @Test
    void testGetPercentage() {
        // Тест 1: Проверка корректности вычисления процента
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal portion = new BigDecimal("25.00");
        String expected = "25,00%";
        String result = getPercentage(amount, portion);
        assertThat(result).isEqualTo(expected);

        // Тест 2: Проверка корректности вычисления процента с дробными числами
        amount = new BigDecimal("100.00");
        portion = new BigDecimal("33.33");
        expected = "33,33%";
        result = getPercentage(amount, portion);
        assertThat(result).isEqualTo(expected);

        // Тест 3: Проверка корректности вычисления процента с нулевым значением
        amount = new BigDecimal("100.00");
        portion = new BigDecimal("0.00");
        expected = "0,00%";
        result = getPercentage(amount, portion);
        assertThat(result).isEqualTo(expected);

        // Тест 4: Проверка корректности вычисления процента с отрицательным значением
        amount = new BigDecimal("100.00");
        portion = new BigDecimal("-25.00");
        expected = "-25,00%";
        result = getPercentage(amount, portion);
        assertThat(result).isEqualTo(expected);
    }
}