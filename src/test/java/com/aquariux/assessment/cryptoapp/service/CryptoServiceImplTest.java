package com.aquariux.assessment.cryptoapp.service;

import com.aquariux.assessment.cryptoapp.dto.CryptoDto;
import com.aquariux.assessment.cryptoapp.mapper.CryptoMapper;
import com.aquariux.assessment.cryptoapp.model.Crypto;
import com.aquariux.assessment.cryptoapp.repository.CryptoRepository;
import com.aquariux.assessment.cryptoapp.service.impl.CryptoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoServiceImplTest {
    @Mock
    private CryptoRepository cryptoRepository;

    @Mock
    private CryptoMapper cryptoMapper;

    @InjectMocks
    CryptoServiceImpl cryptoService;

    @Test
    void testGetLatestBestAggregatedPrice() {
        Crypto crypto = new Crypto(1L, "Bitcoin", "BTCUSDT", new BigDecimal("1865.22"), new BigDecimal("1865.25"));
        CryptoDto cryptoDto = new CryptoDto("Bitcoin", "BTCUSDT", new BigDecimal("1865.22"), new BigDecimal("1865.25"));
        when(cryptoRepository.getLatestBestAggregatedPrice(any())).thenReturn(crypto);
        when(cryptoMapper.convertToDto(crypto)).thenReturn(cryptoDto);
        CryptoDto responseCryptoDto = cryptoService.getLatestBestAggregatedPrice(any());
        assertEquals(new BigDecimal("1865.22"), responseCryptoDto.getBidPrice());
        assertEquals(new BigDecimal("1865.25"), responseCryptoDto.getAskPrice());
    }
}