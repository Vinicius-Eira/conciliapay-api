package com.conciliapay.api.core.application.usecases;

import com.conciliapay.api.core.application.dtos.GatewayTransactionRequestDTO;
import com.conciliapay.api.core.entities.GatewayTransaction;
import com.conciliapay.api.core.entities.Merchant;
import com.conciliapay.api.core.enums.TransactionStatus;
import com.conciliapay.api.core.repositories.GatewayTransactionRepository;
import com.conciliapay.api.core.repositories.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProcessGatewayTransactionUseCase {

    private final GatewayTransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;

    public ProcessGatewayTransactionUseCase(GatewayTransactionRepository transactionRepository, MerchantRepository merchantRepository) {
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
    }

    @Transactional
    public void execute(GatewayTransactionRequestDTO request) {
        Optional<GatewayTransaction> existingTransaction = transactionRepository.findByGatewayId((request.gatewayId()));
        if (existingTransaction.isPresent()) {
            return;
        }

        Merchant merchant = merchantRepository.findByDocument(request.merchantDocument())
                .orElseThrow(() -> new IllegalArgumentException("Lojista não encontrado com o documento: " + request.merchantDocument()));

        GatewayTransaction transaction = new GatewayTransaction();
        transaction.setMerchant(merchant);
        transaction.setGatewayId(request.gatewayId());
        transaction.setAmount(request.amount());
        transaction.setNetAmount(request.netAmount());
        transaction.setStatus(TransactionStatus.PAID);
        transaction.setTransactionDate(request.transactionDate());

        transactionRepository.save(transaction);
    }
}
