package com.investinfo.capital.dto;

import lombok.Getter;
import ru.tinkoff.piapi.contract.v1.MoneyValue;

import java.math.BigDecimal;

@Getter
public class MoneyValueDTO {

    private final BigDecimal moneyValue;

    public MoneyValueDTO(MoneyValue moneyValue) {
        this.moneyValue = getAmount(moneyValue);
    }

    private BigDecimal getAmount(MoneyValue moneyValue) {
        return BigDecimal.valueOf(moneyValue.getUnits()).add(BigDecimal.valueOf(moneyValue.getNano(), 9));
    }
}
