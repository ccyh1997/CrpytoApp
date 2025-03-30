package com.aquariux.assessment.cryptoapp.service;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;

public interface TransactionService {
    TransactionDto createTrade(TransactionDto transactionDto);
}