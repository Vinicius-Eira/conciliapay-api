package com.conciliapay.api.core.application.usecases;

import com.conciliapay.api.core.application.dtos.ReconciliationResponseDTO;
import com.conciliapay.api.core.entities.Reconciliation;
import com.conciliapay.api.core.repositories.ReconciliationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetReconciliationsUseCase {

    private final ReconciliationRepository reconciliationRepository;

    public GetReconciliationsUseCase(ReconciliationRepository reconciliationRepository) {
        this.reconciliationRepository = reconciliationRepository;
    }

    public List<ReconciliationResponseDTO> execute(String merchantDocument) {

        List<Reconciliation> reconciliations = reconciliationRepository.findByMerchant_Document(merchantDocument);

        return reconciliations.stream()
                .map(rec -> new ReconciliationResponseDTO(
                        rec.getId(),
                        rec.getGatewayTransaction().getGatewayId(),
                        rec.getBankTransaction().getBankReference(),
                        rec.getGatewayTransaction().getNetAmount(),
                        rec.getStatus().name(),
                        rec.getReconciledAt()
                ))
                .collect(Collectors.toList());
    }
}
