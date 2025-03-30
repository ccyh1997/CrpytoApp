package com.aquariux.assessment.cryptoapp.mapper;

import com.aquariux.assessment.cryptoapp.dto.CryptoDto;
import com.aquariux.assessment.cryptoapp.model.Crypto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptoMapperTest {
    private final CryptoMapper cryptoMapper = Mappers.getMapper(CryptoMapper.class);

    @Test
    void givenCrypto_whenConvertToDto_thenReturnCryptoDto() {
        Crypto crypto = new Crypto(1L, "Bitcoin", "BTCUSDT", new BigDecimal("1865.22"), new BigDecimal("1865.25"));
        CryptoDto cryptoDto = cryptoMapper.convertToDto(crypto);
        assertEquals("Bitcoin", cryptoDto.getCryptoName());
        assertEquals("BTCUSDT", cryptoDto.getCryptoTicker());
        assertEquals(new BigDecimal("1865.22"), cryptoDto.getBidPrice());
        assertEquals(new BigDecimal("1865.25"), cryptoDto.getAskPrice());
    }
}