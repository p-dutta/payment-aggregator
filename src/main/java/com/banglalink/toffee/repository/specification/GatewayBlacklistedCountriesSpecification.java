package com.banglalink.toffee.repository.specification;

import com.banglalink.toffee.models.schema.BlacklistedCountryEntity;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@NoArgsConstructor
public class GatewayBlacklistedCountriesSpecification {
    public static Specification<BlacklistedCountryEntity> buildGenericBlacklistedCountriesFetchingFilter(UUID gatewayId, String countryCode) {
        Specification<BlacklistedCountryEntity> filters = Specification.where(null);
        if (StringUtils.isNotBlank(countryCode)) {
            filters = filters.and(buildGenericQueryForFilteringCountryCode(countryCode));
        }

        if (gatewayId != null) {
            filters = filters.and(buildGenericQueryForFilteringGatewayId(gatewayId));
        }
        return filters;
    }

    private static Specification<BlacklistedCountryEntity> buildGenericQueryForFilteringCountryCode(String countryCode) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("countryCode"), countryCode));
    }

    private static Specification<BlacklistedCountryEntity> buildGenericQueryForFilteringGatewayId(UUID gatewayId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paymentGateways").get("id"), gatewayId));
    }
}
