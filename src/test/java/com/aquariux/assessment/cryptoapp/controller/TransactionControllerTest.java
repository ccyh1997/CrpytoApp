package com.aquariux.assessment.cryptoapp.controller;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    void testCreateTrade() throws Exception {
        TransactionDto transactionDto = new TransactionDto("investor1", "2025-03-30", "BUY", "ETHUSDT", new BigDecimal("22.54"), new BigDecimal("1865.25"));
        when(transactionService.createTrade(any())).thenReturn(transactionDto);
        mockMvc.perform(post("/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"username\":\"investor1\","
                                + "\"transactionDate\":\"2025-03-30\","
                                + "\"transactionType\":\"BUY\","
                                + "\"cryptoTicker\":\"ETHUSDT\","
                                + "\"units\":22.54,"
                                + "\"unitPrice\":1865.25"
                                + "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("investor1"))
                .andExpect(jsonPath("$.transactionDate").value("2025-03-30"))
                .andExpect(jsonPath("$.transactionType").value("BUY"))
                .andExpect(jsonPath("$.cryptoTicker").value("ETHUSDT"))
                .andExpect(jsonPath("$.units").value(22.54))
                .andExpect(jsonPath("$.unitPrice").value(1865.25));
    }
}