package com.conciliapay.api.core.infrastructure.controllers;

import com.conciliapay.api.core.application.dtos.DashboardMetricsDTO;
import com.conciliapay.api.core.application.dtos.PerformanceMetricsDTO;
import com.conciliapay.api.core.application.usecases.GetPerformanceMetricsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/metrics")
@Tag(name = "Dashboard e Métricas", description = "Rotas para alimentar os gráficos e cards do painel administrativo")
public class MetricsController {

    private final GetPerformanceMetricsUseCase getPerformanceMetricsUseCase;

    public MetricsController(GetPerformanceMetricsUseCase getPerformanceMetricsUseCase) {
        this.getPerformanceMetricsUseCase = getPerformanceMetricsUseCase;
    }

    @GetMapping("/summary")
    @Operation(summary = "Resumo do Dashboard", description = "Retorna os dados consolidados para os cards superiores (total de transações, volume, etc).")
    public ResponseEntity<DashboardMetricsDTO> getSumary() {

        var metrics = new DashboardMetricsDTO(
                new BigDecimal("15450.75"),
                new BigDecimal("12300.00"),
                new BigDecimal("3150.75")
        );
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/performance")
    @Operation(summary = "Métricas de Performance", description = "Retorna o histórico mensal de receitas e despesas para alimentar o gráfico de barras.")
    public ResponseEntity<List<PerformanceMetricsDTO>> getPerformance() {
        List<PerformanceMetricsDTO> performanceData = getPerformanceMetricsUseCase.execute();
        return ResponseEntity.ok(performanceData);
    }
}