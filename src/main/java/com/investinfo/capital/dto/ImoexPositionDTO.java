package com.investinfo.capital.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class ImoexPositionDTO {
    private String figi;
    private String ticker;
    private BigDecimal currentPrice;
    private String classCode;
    private String isin;
    private String name;
    private String sector;
    private String shortName;
}