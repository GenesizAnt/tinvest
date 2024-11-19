package com.investinfo.capital.controller;

import org.junit.jupiter.api.Test;
import ru.tinkoff.piapi.core.models.Money;
import ru.tinkoff.piapi.core.models.Position;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.investinfo.capital.controller.MathUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

class MathUtilsTest {

    @Test
    void testGetPercentageString() {
        // Тест 1: Проверка корректности вычисления процента
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal portion = new BigDecimal("25.00");
        String expected = "25,00%";
        String result = getPercentageString(amount, portion);
        assertThat(result).isEqualTo(expected);

        // Тест 2: Проверка корректности вычисления процента с дробными числами
        amount = new BigDecimal("100.00");
        portion = new BigDecimal("33.33");
        expected = "33,33%";
        result = getPercentageString(amount, portion);
        assertThat(result).isEqualTo(expected);

        // Тест 3: Проверка корректности вычисления процента с нулевым значением
        amount = new BigDecimal("100.00");
        portion = new BigDecimal("0.00");
        expected = "0,00%";
        result = getPercentageString(amount, portion);
        assertThat(result).isEqualTo(expected);

        // Тест 4: Проверка корректности вычисления процента с отрицательным значением
        amount = new BigDecimal("100.00");
        portion = new BigDecimal("-25.00");
        expected = "-25,00%";
        result = getPercentageString(amount, portion);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testGetSumPositionEmptyList() {
        List<Position> positions = new ArrayList<>();
        BigDecimal result = getSumPositions(positions);
        assertThat(result).isZero();
    }

    @Test
    void testGetSumPositionSingleElement() {
        List<Position> positions = new ArrayList<>();
        Position position = createPosition(
                "figi1",
                "instrumentType1",
                new BigDecimal("5"),
                createMoney(new BigDecimal("100.00")),
                new BigDecimal("2.5"),
                createMoney(new BigDecimal("1000.00")),
                new BigDecimal("1.5"),
                createMoney(new BigDecimal("100.00")),
                createMoney(new BigDecimal("100.00")),
                new BigDecimal("1")
        );
        positions.add(position);

        BigDecimal result = getSumPositions(positions);
        assertThat(result).isEqualByComparingTo(new BigDecimal("500.00"));
    }

    @Test
    void testGetSumPositionMultipleElements() {
        List<Position> positions = new ArrayList<>();

        Position position1 = createPosition(
                "figi1",
                "instrumentType1",
                new BigDecimal("5"),
                createMoney(new BigDecimal("100.00")),
                new BigDecimal("2.5"),
                createMoney(new BigDecimal("1000.00")),
                new BigDecimal("1.5"),
                createMoney(new BigDecimal("100.00")),
                createMoney(new BigDecimal("100.00")),
                new BigDecimal("1")
        );
        positions.add(position1);

        Position position2 = createPosition(
                "figi2",
                "instrumentType2",
                new BigDecimal("3"),
                createMoney(new BigDecimal("200.00")),
                new BigDecimal("1.5"),
                createMoney(new BigDecimal("2000.00")),
                new BigDecimal("2.5"),
                createMoney(new BigDecimal("200.00")),
                createMoney(new BigDecimal("200.00")),
                new BigDecimal("2")
        );
        positions.add(position2);

        BigDecimal result = getSumPositions(positions);
        assertThat(result).isEqualByComparingTo(new BigDecimal("1100.00"));
    }

    @Test
    void testGetSumPositionWithZeroQuantity() {
        List<Position> positions = new ArrayList<>();

        Position position = createPosition(
                "figi1",
                "instrumentType1",
                BigDecimal.ZERO,
                createMoney(new BigDecimal("100.00")),
                new BigDecimal("2.5"),
                createMoney(new BigDecimal("1000.00")),
                new BigDecimal("1.5"),
                createMoney(new BigDecimal("100.00")),
                createMoney(new BigDecimal("100.00")),
                new BigDecimal("1")
        );
        positions.add(position);

        BigDecimal result = getSumPositions(positions);
        assertThat(result).isZero();
    }

    @Test
    void testGetSumPositionWithNegativeQuantity() {
        List<Position> positions = new ArrayList<>();

        Position position = createPosition(
                "figi1",
                "instrumentType1",
                new BigDecimal("-5"),
                createMoney(new BigDecimal("100.00")),
                new BigDecimal("2.5"),
                createMoney(new BigDecimal("1000.00")),
                new BigDecimal("1.5"),
                createMoney(new BigDecimal("100.00")),
                createMoney(new BigDecimal("100.00")),
                new BigDecimal("1")
        );
        positions.add(position);

        BigDecimal result = getSumPositions(positions);
        assertThat(result).isEqualByComparingTo(new BigDecimal("-500.00"));
    }

    private Position createPosition(String figi, String instrumentType, BigDecimal quantity, Money averagePositionPrice, BigDecimal expectedYield, Money currentNkd, BigDecimal averagePositionPricePt, Money currentPrice, Money averagePositionPriceFifo, BigDecimal quantityLots) {
        try {
            Constructor<Position> constructor = Position.class.getDeclaredConstructor(String.class, String.class, BigDecimal.class, Money.class, BigDecimal.class, Money.class, BigDecimal.class, Money.class, Money.class, BigDecimal.class);
            constructor.setAccessible(true);
            return constructor.newInstance(figi, instrumentType, quantity, averagePositionPrice, expectedYield, currentNkd, averagePositionPricePt, currentPrice, averagePositionPriceFifo, quantityLots);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Position instance", e);
        }
    }

    private Money createMoney(BigDecimal value) {
        try {
            Constructor<Money> constructor = Money.class.getDeclaredConstructor(String.class, BigDecimal.class);
            constructor.setAccessible(true);
            return constructor.newInstance("USD", value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Money instance", e);
        }
    }

}