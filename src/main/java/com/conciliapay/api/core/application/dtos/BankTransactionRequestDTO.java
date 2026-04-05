package com.conciliapay.api.core.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BankTransactionRequestDTO(
        String merchantDocument,
        String bankReference,
        BigDecimal amount,
        LocalDateTime transactionDate
) {
}


