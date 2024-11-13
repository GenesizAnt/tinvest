package com.investinfo.capital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "imoex_position")
public class ImoexPosition {

    private Integer id;
    private String figi;
    private String tiker;
    private String label;
    private String fullName;
}
