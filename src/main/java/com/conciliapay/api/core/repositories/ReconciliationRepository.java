package com.conciliapay.api.core.repositories;

import com.conciliapay.api.core.entities.Reconciliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ReconciliationRepository extends JpaRepository<Reconciliation, UUID> {

    List<Reconciliation> findByMerchant_Document(String document);

    @Query("SELECT SUM(r.gatewayTransaction.netAmount) FROM Reconciliation r WHERE r.merchant.document = :document")
    BigDecimal sumTotalReconciledAmount(@Param("document") String document);
}