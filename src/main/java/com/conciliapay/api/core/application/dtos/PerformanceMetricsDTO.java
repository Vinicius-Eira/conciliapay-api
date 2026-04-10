package com.conciliapay.api.core.application.dtos;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceMetricsDTO {

    private String month;
    private BigDecimal revenue;
    private BigDecimal expenses;

}