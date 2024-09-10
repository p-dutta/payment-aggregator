package com.banglalink.toffee.repository;

import com.banglalink.toffee.models.schema.PaymentGatewayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentGatewayRepository extends JpaRepository<PaymentGatewayEntity, UUID>, JpaSpecificationExecutor<PaymentGatewayEntity>, PaymentGatewayRepositoryWithAssociation {
    Optional<PaymentGatewayEntity> findByName(String name);
}
