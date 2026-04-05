package com.conciliapay.api.core.application.usecases;

import com.conciliapay.api.core.entities.BankTransaction;
import com.conciliapay.api.core.entities.GatewayTransaction;
import com.conciliapay.api.core.entities.Merchant;
import com.conciliapay.api.core.entities.Reconciliation;
import com.conciliapay.api.core.enums.ReconciliationStatus;
import com.conciliapay.api.core.repositories.BankTransactionRepository;
import com.conciliapay.api.core.repositories.GatewayTransactionRepository;
import com.conciliapay.api.core.repositories.MerchantRepository;
import com.conciliapay.api.core.repositories.ReconciliationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RunReconciliationUseCase {

    private final MerchantRepository merchantRepository;
    private final GatewayTransactionRepository gatewayRepository;
    private final BankTransactionRepository bankRepository;
    private final ReconciliationRepository reconciliationRepository;

    public RunReconciliationUseCase(MerchantRepository merchantRepository, GatewayTransactionRepository gatewayRepository, BankTransactionRepository bankRepository, ReconciliationRepository reconciliationRepository) {
        this.merchantRepository = merchantRepository;
        this.gatewayRepository = gatewayRepository;
        this.bankRepository = bankRepository;
        this.reconciliationRepository = reconciliationRepository;
    }

    @Transactional
    public void execute(String merchantDocument) {

        Merchant merchant = merchantRepository.findByDocument(merchantDocument)
                .orElseThrow(() -> new IllegalArgumentException("Lojista não encontrado."));

        List<GatewayTransaction> pendingGateways = gatewayRepository.findUnreconciledByMerchantDocument(merchantDocument);
        List<BankTransaction> pendingBanks = bankRepository.findUnreconciledByMerchantDocument(merchantDocument);

        for (GatewayTransaction gatewayTx : pendingGateways) {
            for (int i = 0; i < pendingBanks.size(); i++) {
                BankTransaction bankTx = pendingBanks.get(i);

                if (gatewayTx.getNetAmount().compareTo(bankTx.getAmount()) == 0) {

                    Reconciliation reconciliation = new Reconciliation();
                    reconciliation.setMerchant(merchant);
                    reconciliation.setGatewayTransaction(gatewayTx);
                    reconciliation.setBankTransaction(bankTx);
                    reconciliation.setStatus(ReconciliationStatus.MATCHED);

                    reconciliationRepository.save(reconciliation);

                    pendingBanks.remove(i);
                    break;
                }
            }
        }
    }

}
