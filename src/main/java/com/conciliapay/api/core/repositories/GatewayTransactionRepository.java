package com.conciliapay.api.core.repositories;

import com.conciliapay.api.core.entities.GatewayTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GatewayTransactionRepository extends JpaRepository<GatewayTransaction, UUID> {
    Optional<GatewayTransaction> findByGatewayId(String gatewayId);

    @Query("SELECT g FROM GatewayTransaction g WHERE g.merchant.document = :document AND NOT EXISTS (SELECT r FROM Reconciliation r WHERE r.gatewayTransaction = g)")
    List<GatewayTransaction> findUnreconciledByMerchantDocument(@Param("document") String document);
}
