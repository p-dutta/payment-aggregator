package com.banglalink.toffee.repository;

import com.banglalink.toffee.models.schema.PaymentTransactionRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentTransactionRequestRepository extends JpaRepository<PaymentTransactionRequestEntity, Long>, JpaSpecificationExecutor<PaymentTransactionRequestEntity> {
    PaymentTransactionRequestEntity findByTransactionRequestId(UUID transactionId);
}
