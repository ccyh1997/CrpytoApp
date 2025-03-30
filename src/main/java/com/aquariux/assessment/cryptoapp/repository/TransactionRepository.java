package com.aquariux.assessment.cryptoapp.repository;

import com.aquariux.assessment.cryptoapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT COALESCE(SUM(UNITS), 0) FROM TRANSACTIONS WHERE USER_ID = (SELECT USER_ID FROM USERS WHERE USERNAME = ?1) AND CRYPTO_ID = (SELECT CRYPTO_ID FROM CRYPTOS WHERE CRYPTO_TICKER = ?2) AND TRANSACTION_TYPE = 'BUY'", nativeQuery = true)
    BigDecimal getUnitsHeld(String username, String cryptoTicker);

    @Query(value = "SELECT T.TRANSACTION_ID, U.USERNAME, T.TRANSACTION_DATE, T.TRANSACTION_TYPE, C.CRYPTO_TICKER, T.UNITS, T.UNIT_PRICE FROM TRANSACTIONS T JOIN USERS U ON T.USER_ID = U.USER_ID JOIN CRYPTOS C ON T.CRYPTO_ID = C.CRYPTO_ID WHERE U.USERNAME = ?1 ORDER BY T.TRANSACTION_DATE DESC;", nativeQuery = true)
    List<Object[]> getTradingHistory(String username);
}