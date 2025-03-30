package com.aquariux.assessment.cryptoapp.repository;

import com.aquariux.assessment.cryptoapp.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long> {
    @Query(value = "SELECT CRYPTO_ID FROM CRYPTOS WHERE CRYPTO_TICKER = ?1", nativeQuery = true)
    Long findCryptoIdByCryptoTicker(String cryptoTicker);

    @Query(value = "SELECT CRYPTO_IDS, CRYPTO_NAME, CRYPTO_TICKER, BID_PRICE, ASK_PRICE FROM CRYPTOS WHERE CRYPTO_TICKER = ?1", nativeQuery = true)
    Crypto getLatestBestAggregatedPrice(String cryptoTicker);
}