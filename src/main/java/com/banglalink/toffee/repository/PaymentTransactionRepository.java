package com.banglalink.toffee.repository;

import com.banglalink.toffee.models.schema.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Long>, JpaSpecificationExecutor<PaymentTransactionEntity> {
    PaymentTransactionEntity findByTransactionRequestId(UUID transactionId);

    PaymentTransactionEntity findByTransactionId(UUID transactionId);
}

