package com.conciliapay.api.core.entities;

import com.conciliapay.api.core.enums.ReconciliationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reconciliations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reconciliation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_transaction_id", nullable = false, unique = true)
    private GatewayTransaction gatewayTransaction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_transaction_id", nullable = false, unique = true)
    private BankTransaction bankTransaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReconciliationStatus status;

    @Column(name = "reconciled_at", nullable = false)
    private LocalDateTime reconciledAt;

    @PrePersist
    protected void onCreate() {
        this.reconciledAt = LocalDateTime.now();
    }
}
