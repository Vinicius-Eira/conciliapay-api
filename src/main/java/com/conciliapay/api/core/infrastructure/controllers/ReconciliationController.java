package com.conciliapay.api.core.infrastructure.controllers;

import com.conciliapay.api.core.application.dtos.ReconciliationRequestDTO;
import com.conciliapay.api.core.application.dtos.ReconciliationResponseDTO;
import com.conciliapay.api.core.application.usecases.GetReconciliationsUseCase;
import com.conciliapay.api.core.application.usecases.RunReconciliationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reconciliations")
@Tag(name = "Motor de Conciliação", description = "Rotas para processar e consultar o histórico de conciliações")
public class ReconciliationController {

    private final RunReconciliationUseCase runReconciliationUseCase;
    private final GetReconciliationsUseCase getReconciliationsUseCase;

    public ReconciliationController(RunReconciliationUseCase runReconciliationUseCase, GetReconciliationsUseCase getReconciliationsUseCase) {
        this.runReconciliationUseCase = runReconciliationUseCase;
        this.getReconciliationsUseCase = getReconciliationsUseCase;
    }

    @PostMapping("/process/{merchantDocument}")
    @Operation(summary = "Rodar Motor Manualmente", description = "Varre as transações do Lojista e realiza o cruzamento de dados (Gateway x Banco).")
    public ResponseEntity<Map<String, String>> runEngine(@PathVariable String merchantDocument) {

        runReconciliationUseCase.execute(merchantDocument);

        return ResponseEntity.ok(Map.of(
                "message", "Motor de conciliação executado com sucesso!"
        ));
    }

    @GetMapping("/{document}")
    @Operation(summary = "Histórico de Conciliações", description = "Retorna a lista de transações que já foram conciliadas com sucesso.")
    public ResponseEntity<List<ReconciliationResponseDTO>> getHistory(@PathVariable String document) {
        List<ReconciliationResponseDTO> history = getReconciliationsUseCase.execute(document);
        return ResponseEntity.ok(history);
    }
}
