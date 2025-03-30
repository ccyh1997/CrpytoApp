package com.aquariux.assessment.cryptoapp.controller;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.dto.UserDto;
import com.aquariux.assessment.cryptoapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void testGetWalletBalance() throws Exception {
        UserDto userDto = new UserDto("investor1", "John", "Doe", new BigDecimal("1500.75"));
        when(userService.getWalletBalance(any())).thenReturn(userDto);
        mockMvc.perform(get("/users/balance")
                        .param("username", "investor1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("investor1"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.walletBalance").value(1500.75));
    }

    @Test
    void testGetTradingHistory() throws Exception {
        TransactionDto transaction1 = new TransactionDto("investor1", "2025-03-30", "BUY", "ETHUSDT", new BigDecimal("22.54"), new BigDecimal("1865.25"));
        TransactionDto transaction2 = new TransactionDto("investor1", "2025-03-31", "SELL", "ETHUSDT", new BigDecimal("5.00"), new BigDecimal("2000.00"));
        List<TransactionDto> transactionList = Arrays.asList(transaction1, transaction2);
        when(userService.getTradingHistory(any())).thenReturn(transactionList);
        mockMvc.perform(get("/users/history")
                        .param("username", "investor1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("investor1"))
                .andExpect(jsonPath("$[0].transactionDate").value("2025-03-30"))
                .andExpect(jsonPath("$[0].transactionType").value("BUY"))
                .andExpect(jsonPath("$[0].cryptoTicker").value("ETHUSDT"))
                .andExpect(jsonPath("$[0].units").value(22.54))
                .andExpect(jsonPath("$[0].unitPrice").value(1865.25))
                .andExpect(jsonPath("$[1].username").value("investor1"))
                .andExpect(jsonPath("$[1].transactionDate").value("2025-03-31"))
                .andExpect(jsonPath("$[1].transactionType").value("SELL"))
                .andExpect(jsonPath("$[1].cryptoTicker").value("ETHUSDT"))
                .andExpect(jsonPath("$[1].units").value(5.00))
                .andExpect(jsonPath("$[1].unitPrice").value(2000.00));
    }
}