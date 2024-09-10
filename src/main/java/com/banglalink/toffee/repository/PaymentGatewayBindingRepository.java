package com.banglalink.toffee.repository;

import com.banglalink.toffee.models.schema.PaymentGatewayBindingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentGatewayBindingRepository extends JpaRepository<PaymentGatewayBindingEntity, Long> {
    Optional<PaymentGatewayBindingEntity> findByInitiatorMsisdnAndMfsAccount(String initiatorMsisdn, String mfsAccount);

    PaymentGatewayBindingEntity findByRequesterId(UUID requesterId);

    PaymentGatewayBindingEntity findByRequesterIdAndPaywallGatewayId(UUID paymentGatewayId, Long paywallGatewayId);
}