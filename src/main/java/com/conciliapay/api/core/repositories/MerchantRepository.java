package com.conciliapay.api.core.repositories;

import com.conciliapay.api.core.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    Optional<Merchant> findByDocument(String document);
}
