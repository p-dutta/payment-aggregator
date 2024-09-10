package com.banglalink.toffee.models.dto;

import com.banglalink.toffee.models.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionInitDto implements Serializable {
    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("discount_amount")
    private String discountAmount;

    @JsonProperty("device_id")
    private UUID deviceId;

    @JsonProperty("gateway_id")
    private UUID gatewayId;

    @JsonProperty("gateway_channel_id")
    private String gatewayChannelId;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("voucher_code")
    private String voucherCode;

    @JsonProperty("transaction_request_id")
    private UUID transactionRequestId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}

