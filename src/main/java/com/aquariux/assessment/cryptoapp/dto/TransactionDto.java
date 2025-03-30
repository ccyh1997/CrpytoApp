package com.aquariux.assessment.cryptoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String username;

    private String transactionDate;

    private String transactionType;

    private String cryptoTicker;

    private BigDecimal units;

    private BigDecimal unitPrice;
}