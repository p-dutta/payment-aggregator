package com.banglalink.toffee.repository;

import com.banglalink.toffee.models.schema.PaymentGatewayEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface PaymentGatewayRepositoryWithAssociation {
    @Transactional(readOnly = true)
    List<PaymentGatewayEntity> findAllWithAssociatedNodes(Map<String, String> queryParams, Sort sort);
}
