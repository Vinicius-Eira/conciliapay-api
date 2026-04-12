package com.conciliapay.api.core.repositories;

import com.conciliapay.api.core.entities.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, UUID> {
    Optional<BankTransaction> findByBankReference(String bankReference);

    @Query("SELECT b FROM BankTransaction b WHERE b.merchant.document = :document AND NOT EXISTS (SELECT r FROM Reconciliation r WHERE r.bankTransaction = b)")
    List<BankTransaction> findUnreconciledByMerchantDocument(@Param("document") String document);

    Optional<BankTransaction> findFirstByAmountAndTransactionType(BigDecimal amount, String transactionType);
}