package com.conciliapay.api.core.application.usecases;

import com.conciliapay.api.core.entities.BankTransaction;
import com.conciliapay.api.core.entities.GatewayTransaction;
import com.conciliapay.api.core.entities.Reconciliation;
import com.conciliapay.api.core.enums.ReconciliationStatus;
import com.conciliapay.api.core.enums.TransactionStatus;
import com.conciliapay.api.core.repositories.BankTransactionRepository;
import com.conciliapay.api.core.repositories.GatewayTransactionRepository;
import com.conciliapay.api.core.repositories.ReconciliationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReconcileTransactionsUseCase {

    private final GatewayTransactionRepository gatewayRepo;
    private final BankTransactionRepository bankRepo;
    private final ReconciliationRepository reconciliationRepo;

    public ReconcileTransactionsUseCase(GatewayTransactionRepository gatewayRepo, BankTransactionRepository bankRepo, ReconciliationRepository reconciliationRepo) {
        this.gatewayRepo = gatewayRepo;
        this.bankRepo = bankRepo;
        this.reconciliationRepo = reconciliationRepo;
    }

    @Transactional
    public int execute() {
        int reconciliationsCount = 0;

        List<GatewayTransaction> pendingGatewayTransactions = gatewayRepo.findByStatus(TransactionStatus.PAID);

        for (GatewayTransaction gatewayTx : pendingGatewayTransactions) {
            Optional<BankTransaction> matchingBankTx = bankRepo.findFirstByAmountAndTransactionType(gatewayTx.getNetAmount(), "CREDIT");

            if (matchingBankTx.isPresent()) {
                BankTransaction bankTx = matchingBankTx.get();

                Reconciliation reconciliation = new Reconciliation();
                reconciliation.setMerchant(gatewayTx.getMerchant());
                reconciliation.setGatewayTransaction(gatewayTx);
                reconciliation.setBankTransaction(bankTx);
                reconciliation.setStatus(ReconciliationStatus.SUCCESS);

                reconciliationRepo.save(reconciliation);

                gatewayTx.setStatus(TransactionStatus.RECONCILED);
                gatewayRepo.save(gatewayTx);

                reconciliationsCount ++;
            }
        }
        return reconciliationsCount;
    }
}
