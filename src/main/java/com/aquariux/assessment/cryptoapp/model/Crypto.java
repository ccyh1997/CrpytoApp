package com.aquariux.assessment.cryptoapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Crypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crypto_id")
    private Long stockId;

    @Column(name = "crypto_name")
    private String cryptoName;

    @Column(name = "crypto_ticker")
    private String cryptoTicker;

    @Column(name = "bid_price")
    private String lastPrice;

    @Column(name = "ask_price")
    private String askPrice;
}