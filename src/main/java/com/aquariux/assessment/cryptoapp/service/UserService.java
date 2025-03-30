package com.aquariux.assessment.cryptoapp.service;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getWalletBalance(String username);
    List<TransactionDto> getTradingHistory(String username);
}