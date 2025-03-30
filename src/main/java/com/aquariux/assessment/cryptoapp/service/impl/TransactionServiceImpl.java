package com.aquariux.assessment.cryptoapp.service.impl;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.mapper.TransactionMapper;
import com.aquariux.assessment.cryptoapp.model.Transaction;
import com.aquariux.assessment.cryptoapp.repository.CryptoRepository;
import com.aquariux.assessment.cryptoapp.repository.TransactionRepository;
import com.aquariux.assessment.cryptoapp.repository.UserRepository;
import com.aquariux.assessment.cryptoapp.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final CryptoRepository cryptoRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;

    public TransactionServiceImpl(CryptoRepository cryptoRepository, TransactionRepository transactionRepository, TransactionMapper transactionMapper, UserRepository userRepository, UserServiceImpl userService) {
        this.cryptoRepository = cryptoRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public TransactionDto createTrade(TransactionDto transactionDto) {
        if (transactionDto.getTransactionType().equalsIgnoreCase("BUY")) {
            BigDecimal units = transactionDto.getUnits();
            BigDecimal unitPrice = transactionDto.getUnitPrice();
            BigDecimal totalAmount = units.multiply(unitPrice);
            BigDecimal walletBalance = userService.getWalletBalance(transactionDto.getUsername()).getWalletBalance();
            if (walletBalance.compareTo(totalAmount) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds to complete transaction.");
            } else {
                BigDecimal balanceAmount = walletBalance.subtract(totalAmount);
                userRepository.updateWalletBalance(balanceAmount, transactionDto.getUsername());
            }
        }
        if (transactionDto.getTransactionType().equalsIgnoreCase("SELL")) {
            BigDecimal units = transactionDto.getUnits();
            BigDecimal unitsHeld = transactionRepository.getUnitsHeld(transactionDto.getUsername(), transactionDto.getCryptoTicker());
            if (unitsHeld.compareTo(units) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient units to complete transaction.");
            }
        }
        Transaction transaction = transactionMapper.convertToEntity(transactionDto);
        Long userId = userRepository.findUserIdByUsername(transactionDto.getUsername());
        transaction.setUserId(userId);
        transaction.setTransactionDate(String.valueOf(LocalDate.now()));
        Long cryptoId = cryptoRepository.findCryptoIdByCryptoTicker(transactionDto.getCryptoTicker());
        transaction.setCryptoId(cryptoId);
        transactionRepository.save(transaction);
        return transactionMapper.convertToDto(transaction);
    }
}