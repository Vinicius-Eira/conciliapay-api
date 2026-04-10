package com.conciliapay.api.core.infrastructure.controllers;

import com.conciliapay.api.core.application.dtos.BankTransactionRequestDTO;
import com.conciliapay.api.core.application.usecases.ProcessBankTransactionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bank-transactions")
@Tag(name = "Extrato Bancário", description = "Rotas para recebimento e importação de transações bancárias")
public class BankTransactionController {

    private final ProcessBankTransactionUseCase processBankTransactionUseCase;

    public BankTransactionController(ProcessBankTransactionUseCase processBankTransactionUseCase) {
        this.processBankTransactionUseCase = processBankTransactionUseCase;
    }

    @PostMapping("/statement")
    @Operation(summary = "Receber Transação Bancária", description = "Endpoint para registrar uma nova entrada ou saída no extrato do lojista.")
    public ResponseEntity<Void> receiveStatement(@RequestBody BankTransactionRequestDTO requestDTO) {
        processBankTransactionUseCase.execute(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
