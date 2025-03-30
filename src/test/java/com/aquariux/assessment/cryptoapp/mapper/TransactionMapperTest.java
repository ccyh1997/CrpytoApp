package com.aquariux.assessment.cryptoapp.mapper;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.model.Transaction;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionMapperTest {
    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    @Test
    void givenTransaction_whenConvertToDto_thenReturnTransactionDto() {
        Transaction transaction = new Transaction(1L, 1L, "investor1", "2025-03-30", "BUY", 1L, "ETHUSDT", new BigDecimal("22.45"), new BigDecimal("1865.25"));
        TransactionDto transactionDto = transactionMapper.convertToDto(transaction);
        assertEquals("investor1", transactionDto.getUsername());
        assertEquals("2025-03-30", transactionDto.getTransactionDate());
        assertEquals("BUY", transactionDto.getTransactionType());
        assertEquals("ETHUSDT", transactionDto.getCryptoTicker());
        assertEquals(new BigDecimal("22.45"), transactionDto.getUnits());
        assertEquals(new BigDecimal("1865.25"), transactionDto.getUnitPrice());
    }

    @Test
    void givenTransactionDto_whenConvertToEntity_thenReturnTransaction() {
        TransactionDto transactionDto = new TransactionDto("investor1", "2025-03-30", "BUY", "ETHUSDT", new BigDecimal("22.45"), new BigDecimal("1865.25"));
        Transaction transaction = transactionMapper.convertToEntity(transactionDto);
        assertEquals("investor1", transaction.getUsername());
        assertEquals("2025-03-30", transaction.getTransactionDate());
        assertEquals("BUY", transaction.getTransactionType());
        assertEquals("ETHUSDT", transaction.getCryptoTicker());
        assertEquals(new BigDecimal("22.45"), transaction.getUnits());
        assertEquals(new BigDecimal("1865.25"), transaction.getUnitPrice());
    }
}