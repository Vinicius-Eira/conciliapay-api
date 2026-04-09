package com.conciliapay.api.core.application.dtos;

import java.math.BigDecimal;

public record DashboardMetricsDTO(
        BigDecimal grossSales,
        BigDecimal reconciled,
        BigDecimal pending
) {
}
