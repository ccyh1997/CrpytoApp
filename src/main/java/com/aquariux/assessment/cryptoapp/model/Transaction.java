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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "user_id")
    private Long userId;

    @Transient
    private String username;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "crypto_id")
    private Long cryptoId;

    @Transient
    private String cryptoTicker;

    @Column
    private String units;

    @Column(name = "unit_price")
    private String unitPrice;
}