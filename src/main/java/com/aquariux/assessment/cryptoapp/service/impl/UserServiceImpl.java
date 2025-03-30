package com.aquariux.assessment.cryptoapp.service.impl;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.dto.UserDto;
import com.aquariux.assessment.cryptoapp.mapper.TransactionMapper;
import com.aquariux.assessment.cryptoapp.mapper.UserMapper;
import com.aquariux.assessment.cryptoapp.model.Transaction;
import com.aquariux.assessment.cryptoapp.model.User;
import com.aquariux.assessment.cryptoapp.repository.TransactionRepository;
import com.aquariux.assessment.cryptoapp.repository.UserRepository;
import com.aquariux.assessment.cryptoapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public UserDto getWalletBalance(String username) {
        try {
            User user = userRepository.getWalletBalance(username);
            return userMapper.convertToDto(user);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    @Override
    public List<TransactionDto> getTradingHistory(String username) {
        try {
            List<Object[]> transactions = transactionRepository.getTradingHistory(username);
            return transactions.stream()
                    .map(row -> {
                        Long transactionId = ((Integer) row[0]).longValue();
                        LocalDate transactionDate = ((java.sql.Date) row[2]).toLocalDate();
                        String transactionType = (String) row[3];
                        String cryptoTicker = (String) row[4];
                        BigDecimal units = (BigDecimal) row[5];
                        BigDecimal unitPrice = (BigDecimal) row[6];
                        Transaction transaction = new Transaction();
                        transaction.setTransactionId(transactionId);
                        transaction.setUsername(username);
                        transaction.setTransactionDate(String.valueOf(transactionDate));
                        transaction.setTransactionType(transactionType);
                        transaction.setCryptoTicker(cryptoTicker);
                        transaction.setUnits(units);
                        transaction.setUnitPrice(unitPrice);
                        return transactionMapper.convertToDto(transaction);
                    })
                    .toList();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }
}