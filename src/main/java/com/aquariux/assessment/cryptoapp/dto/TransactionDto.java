package com.aquariux.assessment.cryptoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String username;

    private String transactionDate;

    private String transactionType;

    private String cryptoTicker;

    private String units;

    private String unitPrice;
}