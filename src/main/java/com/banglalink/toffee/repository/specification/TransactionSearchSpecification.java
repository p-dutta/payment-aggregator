package com.banglalink.toffee.repository.specification;

import com.banglalink.toffee.models.enums.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.*;

import static com.banglalink.toffee.models.schema.ConstantUtil.*;

public class TransactionSearchSpecification<T> implements Specification<T> {
    private final Map<String, String> queryParams;
    private final TransactionSearchType searchType;
    private final TransactionSearchColumn searchColumn;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean startDatePresent;
    private boolean endDatePresent;

    public TransactionSearchSpecification(Map<String, String> queryParams, TransactionSearchType searchType, TransactionSearchColumn searchColumn) {
        this.queryParams = queryParams;
        this.searchType = searchType;
        this.searchColumn = searchColumn;

        this.startDatePresent = false;
        this.endDatePresent = false;
        this.startDate = LocalDateTime.parse(START_OF_TIME, DATE_TIME_FORMATTER);
        this.endDate = LocalDateTime.parse(START_OF_TIME, DATE_TIME_FORMATTER);

        setStartDateFromQueryParams();
        setEndDateFromQueryParams();
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
        boolean transactionCompleted = searchType.equals(TransactionSearchType.completed);

        // common filters for init/completed transactions
        if (key.equals("startDate") && startDatePresent)
            return filterByStartDate(criteriaBuilder, root);

        else if (key.equals("endDate") && endDatePresent)
            return filterByEndDate(criteriaBuilder, root);

        if (queryParamIsValid) {
            switch (key) {
                case "msisdn" -> {
                    return filterByMsisdn(criteriaBuilder, root, value);
                }
                case "transactionRequestId" -> {
                    return filterByTransactionRequestId(criteriaBuilder, root, value);
                }
                case "gatewayId" -> {
                    return filterByGatewayId(criteriaBuilder, root, UUID.fromString(value));
                }
                case "productId" -> {
                    return filterByProductId(criteriaBuilder, root, value);
                }
                case "paymentType" -> {
                    return filterByPaymentType(criteriaBuilder, root, value);
                }
                case "voucherCode" -> {
                    return filterByVoucherCode(criteriaBuilder, root, value);
                }
            }

            /* completed transactions filters */
            if (transactionCompleted) {
                switch (key) {
                    case "transactionId" -> {
                        return filterByTransactionId(criteriaBuilder, root, value);
                    }
                    case "gatewayTransactionId" -> {
                        return filterByGatewayTransactionId(criteriaBuilder, root, value);
                    }
                    case "status" -> {
                        return filterByTransactionStatus(criteriaBuilder, root, value);
                    }
                    case "ipnStatus" -> {
                        return filterByIpnStatus(criteriaBuilder, root, value);
                    }
                    case "callbackStatus" -> {
                        return filterByCallbackStatus(criteriaBuilder, root, value);
                    }
                }
            }
        }

        return criteriaBuilder.conjunction();
    }

    private Predicate filterByStartDate(CriteriaBuilder cb, Root<T> root) {
        return cb.greaterThanOrEqualTo(root.get(searchColumn.name()), startDate);
    }

    private Predicate filterByEndDate(CriteriaBuilder cb, Root<T> root) {
        return cb.lessThanOrEqualTo(root.get(searchColumn.name()), endDate);
    }

    private Predicate filterByTransactionRequestId(CriteriaBuilder cb, Root<T> root, String value) {
        return cb.equal(root.get("transactionRequestId"), UUID.fromString(value));
    }

    private Predicate filterByMsisdn(CriteriaBuilder builder, Root<T> root, String msisdn) {
        return builder.equal(root.get("msisdn"), msisdn);
    }

    private Predicate filterByProductId(CriteriaBuilder builder, Root<T> root, String productId) {
        return builder.equal(root.get("productId"), productId);
    }

    private Predicate filterByGatewayId(CriteriaBuilder builder, Root<T> root, UUID gatewayId) {
        return builder.equal(root.get("gatewayId"), gatewayId);
    }

    private Predicate filterByPaymentType(CriteriaBuilder builder, Root<T> root, String paymentType) {
        return builder.equal(root.get("paymentType"), PaymentType.valueOf(paymentType));
    }

    private Predicate filterByVoucherCode(CriteriaBuilder builder, Root<T> root, String voucherCode) {
        return builder.equal(root.get("voucherCode"), voucherCode);
    }

    private Predicate filterByTransactionId(CriteriaBuilder builder, Root<T> root, String transactionId) {
        return builder.equal(root.get("transactionId"), UUID.fromString(transactionId));
    }

    private Predicate filterByGatewayTransactionId(CriteriaBuilder builder, Root<T> root, String gatewayTransactionId) {
        return builder.equal(root.get("gatewayTransactionId"), gatewayTransactionId);
    }

    private Predicate filterByTransactionStatus(CriteriaBuilder builder, Root<T> root, String transactionStatus) {
        return builder.equal(root.get("status"), TransactionStatus.valueOf(transactionStatus));
    }

    private Predicate filterByIpnStatus(CriteriaBuilder builder, Root<T> root, String ipnStatus) {
        return builder.equal(root.get("ipnStatus"), GatewayIpnStatus.valueOf(ipnStatus));
    }

    private Predicate filterByCallbackStatus(CriteriaBuilder builder, Root<T> root, String callbackStatus) {
        return builder.equal(root.get("callbackStatus"), GatewayCallbackStatus.valueOf(callbackStatus));
    }

    private boolean hasValidQueryParam(String key) {
        return StringUtils.isNotBlank(queryParams.get(key));
    }

    private void setStartDateFromQueryParams() {
        String date = queryParams.get("startDate");
        if (StringUtils.isNotBlank(date))
            this.startDate = LocalDateTime.parse(date, DATE_TIME_FORMATTER);

        if (!this.startDate.isEqual(LocalDateTime.parse(START_OF_TIME, DATE_TIME_FORMATTER))) this.startDatePresent = true;
    }

    private void setEndDateFromQueryParams() {
        String date = queryParams.get("endDate");
        if (StringUtils.isNotBlank(date))
            this.endDate = LocalDateTime.parse(date, DATE_TIME_FORMATTER);

        if(!this.endDate.isEqual(LocalDateTime.parse(START_OF_TIME, DATE_TIME_FORMATTER))) this.endDatePresent = true;
    }
}
