package com.conciliapay.api.core.application.dtos;

import java.math.BigDecimal;

public record MonthlyChartDTO(
        String month,
        BigDecimal revenue,
        BigDecimal expenses
) {
}
