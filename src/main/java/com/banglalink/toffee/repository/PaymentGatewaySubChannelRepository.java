package com.banglalink.toffee.repository;

import com.banglalink.toffee.models.schema.GatewaySubChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentGatewaySubChannelRepository extends JpaRepository<GatewaySubChannelEntity, Long> {
    List<GatewaySubChannelEntity> findByPaymentGatewaysId(UUID paymentGatewayId);
}
