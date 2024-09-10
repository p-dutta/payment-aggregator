package com.banglalink.toffee.repository.specification;

import com.banglalink.toffee.models.enums.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GatewayImageSpecification<T> implements Specification<T> {
    private final Map<String, String> queryParams;

    public GatewayImageSpecification(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            predicates.add(constructPredicates(root, cb, key, value));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate constructPredicates(Root<T> root, CriteriaBuilder criteriaBuilder, String key, String value) {
        boolean queryParamIsValid = hasValidQueryParam(key);

        if (queryParamIsValid) {
            switch (key) {
                case "slug" -> {
                    return filterBySlug(criteriaBuilder, root, value);
                }
                case "platform" -> {
                    return filterByPlatform(criteriaBuilder, root, value);
                }
                case "provider" -> {
                    return filterByProvider(criteriaBuilder, root, value);
                }
            }
        }

        return criteriaBuilder.conjunction();
    }

    private Predicate filterBySlug(CriteriaBuilder cb, Root<T> root, String slug) {
        return cb.equal(root.get("slug"), slug);
    }

    private Predicate filterByPlatform(CriteriaBuilder cb, Root<T> root, String platform) {
        return cb.equal(root.get("platform"), platform);
    }

    private Predicate filterByProvider(CriteriaBuilder builder, Root<T> root, String provider) {
        return builder.equal(root.get("provider"), provider);
    }

    private boolean hasValidQueryParam(String key) {
        return StringUtils.isNotBlank(queryParams.get(key));
    }
}
