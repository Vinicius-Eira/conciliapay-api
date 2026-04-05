package com.conciliapay.api.core.infrastructure.controllers;

import com.conciliapay.api.core.application.dtos.BankTransactionRequestDTO;
import com.conciliapay.api.core.application.usecases.ProcessBankTransactionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bank-transactions")
public class BankTransactionController {

    private final ProcessBankTransactionUseCase processBankTransactionUseCase;

    public BankTransactionController(ProcessBankTransactionUseCase processBankTransactionUseCase) {
        this.processBankTransactionUseCase = processBankTransactionUseCase;
    }

    @PostMapping("/statement")
    public ResponseEntity<Void> receiveStatement(@RequestBody BankTransactionRequestDTO requestDTO) {
        processBankTransactionUseCase.execute(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
