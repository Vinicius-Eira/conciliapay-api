package com.conciliapay.api.core.application.usecases;

import com.conciliapay.api.core.application.dtos.DashboardDTO;
import com.conciliapay.api.core.repositories.GatewayTransactionRepository;
import com.conciliapay.api.core.repositories.ReconciliationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class GetDashboardMetricsUseCase {

    private final GatewayTransactionRepository gatewayRepo;
    private final ReconciliationRepository reconciliationRepo;

    public GetDashboardMetricsUseCase(GatewayTransactionRepository gatewayRepo, ReconciliationRepository reconciliationRepo) {
        this.gatewayRepo = gatewayRepo;
        this.reconciliationRepo = reconciliationRepo;
    }

    public DashboardDTO execute(String merchantDocument) {
        BigDecimal totalGross = Optional.ofNullable(gatewayRepo.sumTotalGrossAmount(merchantDocument)).orElse(BigDecimal.ZERO);
        BigDecimal totalReconciled = Optional.ofNullable(reconciliationRepo.sumTotalReconciledAmount(merchantDocument)).orElse(BigDecimal.ZERO);
        BigDecimal totalPending = Optional.ofNullable(gatewayRepo.sumPendingLiquidation(merchantDocument)).orElse(BigDecimal.ZERO);

        return new DashboardDTO(totalGross, totalReconciled, totalPending, 12.5);
    }
}
