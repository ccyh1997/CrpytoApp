package com.aquariux.assessment.cryptoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CryptoDto {
    private Long stockId;

    private String cryptoName;

    private String cryptoTicker;

    private String lastPrice;

    private String askPrice;
}