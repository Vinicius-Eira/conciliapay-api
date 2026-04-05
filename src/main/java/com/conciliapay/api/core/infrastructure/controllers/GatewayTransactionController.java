package com.conciliapay.api.core.infrastructure.controllers;

import com.conciliapay.api.core.application.dtos.GatewayTransactionRequestDTO;
import com.conciliapay.api.core.application.usecases.ProcessGatewayTransactionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gateway-transactions")
public class GatewayTransactionController {

    private final ProcessGatewayTransactionUseCase processGatewayTransactionUseCase;

    public GatewayTransactionController(ProcessGatewayTransactionUseCase processGatewayTransactionUseCase) {
        this.processGatewayTransactionUseCase = processGatewayTransactionUseCase;
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveWebhook(@RequestBody GatewayTransactionRequestDTO requestDTO) {

        processGatewayTransactionUseCase.execute(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
