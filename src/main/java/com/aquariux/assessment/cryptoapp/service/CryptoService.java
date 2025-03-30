package com.aquariux.assessment.cryptoapp.service;

import com.aquariux.assessment.cryptoapp.dto.CryptoDto;

public interface CryptoService {
    CryptoDto getLatestBestAggregatedPrice(String cryptoTicker);
}