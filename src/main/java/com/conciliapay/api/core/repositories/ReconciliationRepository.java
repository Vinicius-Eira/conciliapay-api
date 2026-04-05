package com.conciliapay.api.core.repositories;

import com.conciliapay.api.core.entities.Reconciliation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReconciliationRepository extends JpaRepository<Reconciliation, UUID> {

    List<Reconciliation> findByMerchant_Document(String document);
}