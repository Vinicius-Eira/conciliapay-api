package com.conciliapay.api.core.application.usecases;

import com.conciliapay.api.core.application.dtos.BankTransactionRequestDTO;
import com.conciliapay.api.core.entities.BankTransaction;
import com.conciliapay.api.core.entities.Merchant;
import com.conciliapay.api.core.repositories.BankTransactionRepository;
import com.conciliapay.api.core.repositories.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProcessBankTransactionUseCase {

    private final BankTransactionRepository bankTransactionRepository;
    private final MerchantRepository merchantRepository;

    public ProcessBankTransactionUseCase(BankTransactionRepository bankTransactionRepository, MerchantRepository merchantRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.merchantRepository = merchantRepository;
    }

    @Transactional
    public void execute(BankTransactionRequestDTO request) {

        Optional<BankTransaction> existingTransaction = bankTransactionRepository.findByBankReference(request.bankReference());
        if (existingTransaction.isPresent()) {
            return;
        }

        Merchant merchant = merchantRepository.findByDocument(request.merchantDocument())
                .orElseThrow(() -> new IllegalArgumentException("Lojista não encontraado com o documento: " + request.merchantDocument()));

        BankTransaction transaction = new BankTransaction();
        transaction.setMerchant(merchant);
        transaction.setBankReference(request.bankReference());
        transaction.setAmount(request.amount());
        transaction.setTransactionDate(request.transactionDate());

        bankTransactionRepository.save(transaction);
    }
}
