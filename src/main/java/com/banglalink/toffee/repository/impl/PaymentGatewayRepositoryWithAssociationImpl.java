package com.banglalink.toffee.repository.impl;

import com.banglalink.toffee.models.enums.GatewayType;
import com.banglalink.toffee.models.schema.PaymentGatewayEntity;
import com.banglalink.toffee.repository.PaymentGatewayRepositoryWithAssociation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

public class PaymentGatewayRepositoryWithAssociationImpl implements PaymentGatewayRepositoryWithAssociation {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentGatewayEntity> findAllWithAssociatedNodes(Map<String, String> queryParams, Sort sort) {
        StringBuilder jpql = new StringBuilder("SELECT pgw FROM PaymentGatewayEntity pgw");

        String rawQuery = String.join(" ", jpql, "LEFT JOIN FETCH pgw.gatewaySubChannels");
        TypedQuery<PaymentGatewayEntity> typedQuery = entityManager.createQuery(rawQuery, PaymentGatewayEntity.class);
        List<PaymentGatewayEntity> gateways = typedQuery.getResultList();

        // separate LEFT JOINS to avoid MultiBagFetch exception
        rawQuery = String.join(" ", jpql, "LEFT JOIN FETCH pgw.gatewayBlacklistedCountry bc WHERE 1=1", buildDynamicQuery(queryParams), buildOrderByClause(sort));
        typedQuery = applyDynamicFilter(rawQuery, queryParams);

        return typedQuery.getResultList();
    }


    private String buildOrderByClause(Sort sort) {
        StringBuilder order = new StringBuilder();
        if (sort != null && sort.isSorted()) {
            List<String> orders = sort.map(o -> String.format("pgw.%s %s ", o.getProperty(), (o.isAscending() ? ASC.name() : DESC.name()))).toList();
            order.append("ORDER BY ").append(String.join(", ", orders));
        }

        return order.toString();
    }

    private String buildDynamicQuery(Map<String, String> query) {
        StringBuilder target = new StringBuilder();

        for (Map.Entry<String, String> entry : query.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (hasValidQueryParam(value)) {
                switch (key) {
                    case "gatewayType", "provider" -> target.append(String.format("AND pgw.%s = :%s ", key, key));
                }
            }
        }

        return target.toString();
    }

    private TypedQuery<PaymentGatewayEntity> applyDynamicFilter(String rawQuery, Map<String, String> queryParams) {
        TypedQuery<PaymentGatewayEntity> typedQuery = entityManager.createQuery(rawQuery, PaymentGatewayEntity.class);
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (hasValidQueryParam(value)) {
                switch (key) {
                    case "gatewayType" -> typedQuery.setParameter("gatewayType", GatewayType.valueOf(value));

                    case "provider" -> typedQuery.setParameter("provider", value);
                }
            }
        }
        return typedQuery;
    }

    private Boolean hasValidQueryParam(String value) { return StringUtils.isNotBlank(value); }
}
