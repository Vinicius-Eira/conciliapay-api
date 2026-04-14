package com.conciliapay.api.core.infrastructure.controllers;

import com.conciliapay.api.core.application.dtos.DashboardDTO;
import com.conciliapay.api.core.application.usecases.GetDashboardMetricsUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "Métricas consolidadas para a visão geral")
public class DashboardController {

    private final GetDashboardMetricsUseCase getDashboardMetricsUseCase;

    public DashboardController(GetDashboardMetricsUseCase getDashboardMetricsUseCase) {
        this.getDashboardMetricsUseCase = getDashboardMetricsUseCase;
    }

    @GetMapping("/{merchantDocument}")
    public ResponseEntity<DashboardDTO> getMetrics(@PathVariable String merchantDocument) {
        return ResponseEntity.ok(getDashboardMetricsUseCase.execute(merchantDocument));
    }
}
