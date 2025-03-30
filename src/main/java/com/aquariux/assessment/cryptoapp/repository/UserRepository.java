package com.aquariux.assessment.cryptoapp.repository;

import com.aquariux.assessment.cryptoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT USER_ID, USERNAME, FIRST_NAME, LAST_NAME, WALLET_BALANCE FROM USERS WHERE USERNAME = ?1", nativeQuery = true)
    User getWalletBalance(String username);
}