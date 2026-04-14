package com.conciliapay.api.core.application.dtos;

import java.math.BigDecimal;

public record DashboardDTO(
        BigDecimal totalGrossSales,
        BigDecimal totalReconciled,
        BigDecimal totalPendingLiquidation,
        Double salesGrowthPercentage
) {
}
