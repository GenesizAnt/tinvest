package com.investinfo.capital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "imoex_position")
public class ImoexPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "figi")
    private String figi;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "classCode")
    private String classCode;

    @Column(name = "isin")
    private String isin;

    @Column(name = "name")
    private String name;

    @Column(name = "sector")
    private String sector;

    @Column(name = "shortname")
    private String shortName;
}
