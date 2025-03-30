package com.aquariux.assessment.cryptoapp.controller;

import com.aquariux.assessment.cryptoapp.dto.CryptoDto;
import com.aquariux.assessment.cryptoapp.service.CryptoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CryptoController.class)
class CryptoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CryptoService cryptoService;

    @Test
    void testGetLatestBestAggregatedPrice() throws Exception {
        CryptoDto cryptoDto = new CryptoDto("Bitcoin", "BTCUSDT", new BigDecimal("1865.22"), new BigDecimal("1865.25"));
        when(cryptoService.getLatestBestAggregatedPrice(any())).thenReturn(cryptoDto);
        mockMvc.perform(get("/cryptos/price")
                        .param("cryptoTicker", "BTCUSDT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cryptoName").value("Bitcoin"))
                .andExpect(jsonPath("$.cryptoTicker").value("BTCUSDT"))
                .andExpect(jsonPath("$.bidPrice").value(new BigDecimal("1865.22")))
                .andExpect(jsonPath("$.askPrice").value(new BigDecimal("1865.25")));
    }
}