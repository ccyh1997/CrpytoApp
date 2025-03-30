package com.aquariux.assessment.cryptoapp.repository;

import com.aquariux.assessment.cryptoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT USER_ID FROM USERS WHERE USERNAME = ?1", nativeQuery = true)
    Long findUserIdByUsername(String username);

    @Query(value = "SELECT USER_ID, USERNAME, FIRST_NAME, LAST_NAME, WALLET_BALANCE FROM USERS WHERE USERNAME = ?1", nativeQuery = true)
    User getWalletBalance(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USERS SET WALLET_BALANCE = ?1 WHERE USERNAME = ?2", nativeQuery = true)
    void updateWalletBalance(BigDecimal balanceAmount, String username);
}