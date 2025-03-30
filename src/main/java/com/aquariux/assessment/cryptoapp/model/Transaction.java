package com.aquariux.assessment.cryptoapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Transactions")
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
    private BigDecimal units;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;
}