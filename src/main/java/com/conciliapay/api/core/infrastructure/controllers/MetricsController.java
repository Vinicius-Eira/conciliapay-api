package com.conciliapay.api.core.infrastructure.controllers;

import com.conciliapay.api.core.application.dtos.DashboardMetricsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/metrics")
public class MetricsController {

    @GetMapping("/summary")
    public ResponseEntity<DashboardMetricsDTO> getSumary() {

        var metrics = new DashboardMetricsDTO(
                new BigDecimal("15450.75"),
                new BigDecimal("12300.00"),
                new BigDecimal("3150.75")
        );
        return ResponseEntity.ok(metrics);
    }
}
