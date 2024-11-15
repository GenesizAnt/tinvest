package com.investinfo.capital.telegram;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PrepareResponse {

    public String getAllCommand() {
        return """
                /amount - Общая статистика портфеля
                /position - Позиции без облигаций
                день - Показать номер дня в году
                /diagram_sec - соотношение секторов
                """;
    }

    public String getDayOfYear() {
        return String.format("Сегодня %d день %d года", LocalDate.now().getDayOfYear(), LocalDate.now().getYear());
    }
}
