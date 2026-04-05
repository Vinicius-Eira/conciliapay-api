package com.conciliapay.api.core.infrastructure.controllers;

import com.conciliapay.api.core.application.dtos.ReconciliationRequestDTO;
import com.conciliapay.api.core.application.dtos.ReconciliationResponseDTO;
import com.conciliapay.api.core.application.usecases.GetReconciliationsUseCase;
import com.conciliapay.api.core.application.usecases.RunReconciliationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reconciliations")
public class ReconciliationController {

    private final RunReconciliationUseCase runReconciliationUseCase;
    private final GetReconciliationsUseCase getReconciliationsUseCase;

    public ReconciliationController(RunReconciliationUseCase runReconciliationUseCase, GetReconciliationsUseCase getReconciliationsUseCase) {
        this.runReconciliationUseCase = runReconciliationUseCase;
        this.getReconciliationsUseCase = getReconciliationsUseCase;
    }

    @PostMapping("/run")
    public ResponseEntity<String> runEngine(@RequestBody ReconciliationRequestDTO requestDTO) {
        runReconciliationUseCase.execute(requestDTO.merchantDocument());
        return ResponseEntity.ok("Motor de conciliação executado com sucesso!");
    }

    @GetMapping("/{document}")
    public ResponseEntity<List<ReconciliationResponseDTO>> getHistory(@PathVariable String document) {
        List<ReconciliationResponseDTO> history = getReconciliationsUseCase.execute(document);
        return ResponseEntity.ok(history);
    }
}
