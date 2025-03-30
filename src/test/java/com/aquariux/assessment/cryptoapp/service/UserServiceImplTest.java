package com.aquariux.assessment.cryptoapp.service;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.dto.UserDto;
import com.aquariux.assessment.cryptoapp.mapper.TransactionMapper;
import com.aquariux.assessment.cryptoapp.mapper.UserMapper;
import com.aquariux.assessment.cryptoapp.model.Transaction;
import com.aquariux.assessment.cryptoapp.model.User;
import com.aquariux.assessment.cryptoapp.repository.TransactionRepository;
import com.aquariux.assessment.cryptoapp.repository.UserRepository;
import com.aquariux.assessment.cryptoapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testGetWalletBalance() {
        User user = new User(1L, "investor1", "John", "Doe", new BigDecimal("1865.25"));
        UserDto userDto = new UserDto("investor1", "John", "Doe", new BigDecimal("1865.25"));
        when(userRepository.getWalletBalance(any())).thenReturn(user);
        when(userMapper.convertToDto(user)).thenReturn(userDto);
        UserDto responseUserDto = userService.getWalletBalance(any());
        assertEquals(new BigDecimal("1865.25"), responseUserDto.getWalletBalance());
    }

    @Test
    void testGetTradingHistory() {
        Object[] object1 = new Object[] {1, "investor1", Date.valueOf("2025-03-30"), "BUY", "ETHUSDT", new BigDecimal("22.45"), new BigDecimal("1865.25")};
        Object[] object2 = new Object[] {1, "investor1", Date.valueOf("2025-03-30"), "SELL", "ETHUSDT", new BigDecimal("20.15"), new BigDecimal("1905.11")};
        List<Object[]> objectList = Arrays.asList(object1, object2);
        TransactionDto transactionDto1 = new TransactionDto("investor1", "2025-03-30", "BUY", "ETHUSDT", new BigDecimal("22.45"), new BigDecimal("1865.25"));
        TransactionDto transactionDto2 = new TransactionDto("investor1", "2025-03-30", "SELL", "ETHUSDT", new BigDecimal("20.15"), new BigDecimal("1905.11"));
        when(transactionRepository.getTradingHistory(any())).thenReturn(objectList);
        when(transactionMapper.convertToDto(any(Transaction.class))).thenReturn(transactionDto1).thenReturn(transactionDto2);
        List<TransactionDto> transactionDtoList = userService.getTradingHistory("investor1");
        assertNotNull(transactionDtoList);
        assertEquals(2, transactionDtoList.size());
        assertTrue(transactionDtoList.contains(transactionDto1));
        assertTrue(transactionDtoList.contains(transactionDto2));
    }
}