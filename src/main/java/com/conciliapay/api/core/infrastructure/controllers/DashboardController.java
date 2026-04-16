package com.conciliapay.api.core.infrastructure.controllers;

import com.conciliapay.api.core.application.dtos.DashboardDTO;
import com.conciliapay.api.core.application.dtos.MonthlyChartDTO;
import com.conciliapay.api.core.application.usecases.GetDashboardChartUseCase;
import com.conciliapay.api.core.application.usecases.GetDashboardMetricsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "Métricas consolidadas para a visão geral")
public class DashboardController {

    private final GetDashboardMetricsUseCase getDashboardMetricsUseCase;
    private final GetDashboardChartUseCase getDashboardChartUseCase;

    public DashboardController(GetDashboardMetricsUseCase getDashboardMetricsUseCase, GetDashboardChartUseCase getDashboardChartUseCase) {
        this.getDashboardMetricsUseCase = getDashboardMetricsUseCase;
        this.getDashboardChartUseCase = getDashboardChartUseCase;
    }

    @GetMapping("/{merchantDocument}")
    public ResponseEntity<DashboardDTO> getMetrics(@PathVariable String merchantDocument) {
        return ResponseEntity.ok(getDashboardMetricsUseCase.execute(merchantDocument));
    }

    @GetMapping("/{merchantDocument}/chart")
    @Operation(summary = "Dados do Gráfico Mensal", description = "Retorna receitas e despesas agrupadas pelos últimos 6 meses.")
    public ResponseEntity<List<MonthlyChartDTO>> getChartData(@PathVariable String merchantDocument) {
        return ResponseEntity.ok(getDashboardChartUseCase.execute(merchantDocument));
    }
}
