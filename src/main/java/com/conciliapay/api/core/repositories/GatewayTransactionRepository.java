package com.conciliapay.api.core.repositories;

import com.conciliapay.api.core.entities.GatewayTransaction;
import com.conciliapay.api.core.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GatewayTransactionRepository extends JpaRepository<GatewayTransaction, UUID> {
    Optional<GatewayTransaction> findByGatewayId(String gatewayId);

    List<GatewayTransaction> findByStatus(TransactionStatus status);

    @Query("SELECT g FROM GatewayTransaction g WHERE g.merchant.document = :document AND NOT EXISTS (SELECT r FROM Reconciliation r WHERE r.gatewayTransaction = g)")
    List<GatewayTransaction> findUnreconciledByMerchantDocument(@Param("document") String document);

    @Query("SELECT SUM(g.amount) FROM GatewayTransaction g WHERE g.merchant.document = :document")
    BigDecimal sumTotalGrossAmount(@Param("document") String document);

    @Query("SELECT SUM(g.amount) FROM GatewayTransaction g WHERE g.merchant.document = :document AND g.status = 'PAID'")
    BigDecimal sumPendingLiquidation(@Param("document") String document);
}
