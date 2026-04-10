package com.conciliapay.api.core.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record GatewayTransactionRequestDTO(
        String merchantDocument,
        String gatewayId,
        BigDecimal amount,
        BigDecimal netAmount,
        LocalDateTime transactionDate,
        String paymentMethod,
        Integer installments,
        String nsu,
        LocalDate expectedPaymentDate

) {
}
