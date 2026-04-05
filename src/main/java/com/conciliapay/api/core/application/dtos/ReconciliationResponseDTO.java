package com.conciliapay.api.core.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReconciliationResponseDTO(
        UUID id,
        String gatewayId,
        String bankReference,
        BigDecimal amount,
        String status,
        LocalDateTime reconciledAt
) {
}
