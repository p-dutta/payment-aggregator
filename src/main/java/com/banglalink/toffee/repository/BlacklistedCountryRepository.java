package com.banglalink.toffee.repository;

import com.banglalink.toffee.models.schema.BlacklistedCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlacklistedCountryRepository extends JpaRepository<BlacklistedCountryEntity, Long>, JpaSpecificationExecutor<BlacklistedCountryEntity> {
    List<BlacklistedCountryEntity> findAllByPaymentGatewaysId(UUID gatewayId);

    BlacklistedCountryEntity findByCountryCodeAndPaymentGatewaysId(String countryCode, UUID paymentGatewayId);
}
