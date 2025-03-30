package com.aquariux.assessment.cryptoapp.controller;

import com.aquariux.assessment.cryptoapp.dto.TransactionDto;
import com.aquariux.assessment.cryptoapp.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/trade")
    public ResponseEntity<TransactionDto> createTrade(@Valid @RequestBody TransactionDto transactionDto) { // assumption made here is that valid data is provided as there is validation on the frontend
        TransactionDto createdtTransactionDto = transactionService.createTrade(transactionDto);
        return ResponseEntity.ok(createdtTransactionDto);
    }
}