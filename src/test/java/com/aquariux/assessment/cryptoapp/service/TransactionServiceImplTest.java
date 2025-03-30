package com.aquariux.assessment.cryptoapp.service;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.dto.UserDto;
import com.aquariux.assessment.cryptoapp.mapper.TransactionMapper;
import com.aquariux.assessment.cryptoapp.model.Transaction;
import com.aquariux.assessment.cryptoapp.repository.CryptoRepository;
import com.aquariux.assessment.cryptoapp.repository.TransactionRepository;
import com.aquariux.assessment.cryptoapp.repository.UserRepository;
import com.aquariux.assessment.cryptoapp.service.impl.TransactionServiceImpl;
import com.aquariux.assessment.cryptoapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private CryptoRepository cryptoRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    void testCreateBuyTradeBuySuccess() {
        TransactionDto transactionDto = new TransactionDto("investor1", "2025-03-30", "BUY", "ETHUSDT", new BigDecimal("10.33"), new BigDecimal("1865.25"));
        Transaction transaction = new Transaction(1L, 1L, "investor1", "2025-03-30", "BUY", 2L, "ETHUSDT", new BigDecimal("10.33"), new BigDecimal("1865.25"));
        when(userService.getWalletBalance(transactionDto.getUsername())).thenReturn(new UserDto("investor1", "John", "Doe", new BigDecimal("50000.00")));
        doNothing().when(userRepository).updateWalletBalance(new BigDecimal("30731.9675"), "investor1");
        when(transactionMapper.convertToEntity(transactionDto)).thenReturn(transaction);
        when(userRepository.findUserIdByUsername("investor1")).thenReturn(1L);
        when(cryptoRepository.findCryptoIdByCryptoTicker("ETHUSDT")).thenReturn(2L);
        when(transactionMapper.convertToDto(transaction)).thenReturn(transactionDto);
        TransactionDto responseTransactionDto = transactionService.createTrade(transactionDto);
        assertEquals(transactionDto.getUsername(), responseTransactionDto.getUsername());
        assertEquals(transactionDto.getTransactionType(), responseTransactionDto.getTransactionType());
        assertEquals(transactionDto.getCryptoTicker(), responseTransactionDto.getCryptoTicker());
        assertEquals(transactionDto.getUnits(), responseTransactionDto.getUnits());
        assertEquals(transactionDto.getUnitPrice(), responseTransactionDto.getUnitPrice());
    }

    @Test
    void testCreateBuyTradeInsufficientFunds() {
        TransactionDto transactionDto = new TransactionDto("investor1", "2025-03-30", "BUY", "ETHUSDT", new BigDecimal("50.33"), new BigDecimal("1865.25"));
        when(userService.getWalletBalance(transactionDto.getUsername())).thenReturn(new UserDto("investor1", "John", "Doe", new BigDecimal("50000.00")));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            transactionService.createTrade(transactionDto);
        });
        assertEquals("Insufficient funds to complete transaction.", exception.getReason());
    }

    @Test
    void testCreateTradeSellSuccess() {
        TransactionDto transactionDto = new TransactionDto("investor1", "2025-03-30", "SELL", "ETHUSDT", new BigDecimal("10.33"), new BigDecimal("1865.25"));
        Transaction transaction = new Transaction(1L, 1L, "investor1", "2025-03-30", "SELL", 2L, "ETHUSDT", new BigDecimal("10.33"), new BigDecimal("1865.25"));
        when(transactionRepository.getUnitsHeld(transactionDto.getUsername(), transactionDto.getCryptoTicker())).thenReturn(new BigDecimal("1223.25"));
        when(userService.getWalletBalance(transactionDto.getUsername())).thenReturn(new UserDto("investor1", "John", "Doe", new BigDecimal("50000.00")));
        doNothing().when(userRepository).updateWalletBalance(new BigDecimal("69268.0325"), "investor1");
        when(transactionMapper.convertToEntity(transactionDto)).thenReturn(transaction);
        when(userRepository.findUserIdByUsername("investor1")).thenReturn(1L);
        when(cryptoRepository.findCryptoIdByCryptoTicker("ETHUSDT")).thenReturn(2L);
        when(transactionMapper.convertToDto(transaction)).thenReturn(transactionDto);
        TransactionDto responseTransactionDto = transactionService.createTrade(transactionDto);
        assertEquals(transactionDto.getUsername(), responseTransactionDto.getUsername());
        assertEquals(transactionDto.getTransactionType(), responseTransactionDto.getTransactionType());
        assertEquals(transactionDto.getCryptoTicker(), responseTransactionDto.getCryptoTicker());
        assertEquals(transactionDto.getUnits(), responseTransactionDto.getUnits());
        assertEquals(transactionDto.getUnitPrice(), responseTransactionDto.getUnitPrice());
    }

    @Test
    void testCreateTradeSellInsufficientUnits() {
        TransactionDto transactionDto = new TransactionDto("investor1", "2025-03-30", "SELL", "ETHUSDT", new BigDecimal("10.33"), new BigDecimal("1865.25"));
        when(transactionRepository.getUnitsHeld(transactionDto.getUsername(), transactionDto.getCryptoTicker())).thenReturn(new BigDecimal("7.32"));
        when(userService.getWalletBalance(transactionDto.getUsername())).thenReturn(new UserDto("investor1", "John", "Doe", new BigDecimal("50000.00")));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            transactionService.createTrade(transactionDto);
        });
        assertEquals("Insufficient units to complete transaction.", exception.getReason());
    }
}