package com.aquariux.assessment.cryptoapp.repository;

import com.aquariux.assessment.cryptoapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT T.TRANSACTION_ID, U.USERNAME, T.TRANSACTION_DATE, T.TRANSACTION_TYPE, C.CRYPTO_TICKER, T.UNITS, T.UNIT_PRICE FROM TRANSACTIONS T JOIN USERS U ON T.USER_ID = U.USER_ID JOIN CRYPTOS C ON T.CRYPTO_ID = C.CRYPTO_ID WHERE U.USERNAME = ?1 ORDER BY T.TRANSACTION_DATE DESC;", nativeQuery = true)
    List<Object[]> getTradingHistory(String username);
}