package com.conciliapay.api.core.application.usecases;

import com.conciliapay.api.core.application.dtos.PerformanceMetricsDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class GetPerformanceMetricsUseCase {

    public List<PerformanceMetricsDTO> execute() {
        // Mock: Dados falsos para testar o gráfico no Front-end
        return Arrays.asList(
                new PerformanceMetricsDTO("Jan", new BigDecimal("15000.00"), new BigDecimal("8000.00")),
                new PerformanceMetricsDTO("Fev", new BigDecimal("18000.00"), new BigDecimal("9500.00")),
                new PerformanceMetricsDTO("Mar", new BigDecimal("14500.00"), new BigDecimal("7000.00")),
                new PerformanceMetricsDTO("Abr", new BigDecimal("22000.00"), new BigDecimal("11000.00")),
                new PerformanceMetricsDTO("Mai", new BigDecimal("25000.00"), new BigDecimal("12500.00")),
                new PerformanceMetricsDTO("Jun", new BigDecimal("28000.00"), new BigDecimal("14000.00"))
        );
    }
}