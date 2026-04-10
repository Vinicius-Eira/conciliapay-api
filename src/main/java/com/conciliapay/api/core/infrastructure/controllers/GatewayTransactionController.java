package com.conciliapay.api.core.infrastructure.controllers;

import com.conciliapay.api.core.application.dtos.GatewayTransactionRequestDTO;
import com.conciliapay.api.core.application.usecases.ProcessGatewayTransactionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gateway-transactions")
@Tag(name = "Gateway de Pagamento", description = "Rotas para recebimento de webhooks e transações de vendas do Gateway")
public class GatewayTransactionController {

    private final ProcessGatewayTransactionUseCase processGatewayTransactionUseCase;

    public GatewayTransactionController(ProcessGatewayTransactionUseCase processGatewayTransactionUseCase) {
        this.processGatewayTransactionUseCase = processGatewayTransactionUseCase;
    }

    @PostMapping("/webhook")
    @Operation(summary = "Receber Venda (Webhook)", description = "Endpoint para o Gateway enviar o aviso de uma nova venda realizada.")
    public ResponseEntity<Void> receiveWebhook(@RequestBody GatewayTransactionRequestDTO requestDTO) {
        processGatewayTransactionUseCase.execute(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
