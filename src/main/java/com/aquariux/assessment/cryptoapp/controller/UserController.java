package com.aquariux.assessment.cryptoapp.controller;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.dto.UserDto;
import com.aquariux.assessment.cryptoapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/balance")
    public ResponseEntity<UserDto> getWalletBalance(@RequestParam() String username) {
        UserDto userDto = userService.getWalletBalance(username);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionDto>> getTradingHistory(@RequestParam() String username) {
        List<TransactionDto> transactionList = userService.getTradingHistory(username);
        return ResponseEntity.ok(transactionList);
    }
}