package com.investinfo.capital.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.tinkoff.piapi.core.models.Money;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class PositionDTO {
    private String figi;
    private BigDecimal currentPrice;

    public PositionDTO(String figi, Money currentPrice) {
        this.figi = figi;
        this.currentPrice = currentPrice.getValue();
    }
}
