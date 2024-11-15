package com.investinfo.capital.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PositionDTO {
    private String figi;
    private String ticker;
    private String classCode;
    private String isin;
    private String name;
    private String sector;
    private String shortName;
}