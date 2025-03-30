package com.aquariux.assessment.cryptoapp.service.impl;

import com.aquariux.assessment.cryptoapp.dto.CryptoDto;
import com.aquariux.assessment.cryptoapp.mapper.CryptoMapper;
import com.aquariux.assessment.cryptoapp.model.Crypto;
import com.aquariux.assessment.cryptoapp.repository.CryptoRepository;
import com.aquariux.assessment.cryptoapp.service.CryptoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CryptoServiceImpl implements CryptoService {
    private final CryptoRepository cryptoRepository;
    private final CryptoMapper cryptoMapper;

    public CryptoServiceImpl(CryptoRepository cryptoRepository, CryptoMapper cryptoMapper) {
        this.cryptoRepository = cryptoRepository;
        this.cryptoMapper = cryptoMapper;
    }

    @Override
    public CryptoDto getLatestBestAggregatedPrice(String cryptoTicker) {
        try {
            Crypto crypto = cryptoRepository.getLatestBestAggregatedPrice(cryptoTicker);
            return cryptoMapper.convertToDto(crypto);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }
}