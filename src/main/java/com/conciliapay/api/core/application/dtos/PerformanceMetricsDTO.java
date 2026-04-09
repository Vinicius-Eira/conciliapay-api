package com.conciliapay.api.core.application.dtos;

import java.math.BigDecimal;

public class PerformanceMetricsDTO {

    private String month;
    private BigDecimal revenue;
    private BigDecimal expenses;

    public PerformanceMetricsDTO() {}

    public PerformanceMetricsDTO(String month, BigDecimal revenue, BigDecimal expenses) {
        this.month = month;
        this.revenue = revenue;
        this.expenses = expenses;
    }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }

    public BigDecimal getExpenses() { return expenses; }
    public void setExpenses(BigDecimal expenses) { this.expenses = expenses; }
}