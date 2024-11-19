package com.investinfo.capital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fire")
public class Fire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "active")
    private BigDecimal active;

    @Column(name = "investments")
    private BigDecimal investments;

    @Column(name = "dividend")
    private BigDecimal dividend;

    @Column(name = "commission")
    private BigDecimal commission;

    @Column(name = "profitability")
    private BigDecimal profitability;

    @Column(name = "annualyield")
    private BigDecimal annualYield;

    @Column(name = "date")
    private LocalDate date;
}
