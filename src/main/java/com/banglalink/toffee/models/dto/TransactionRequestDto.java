package com.banglalink.toffee.models.dto;

import com.banglalink.toffee.models.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto implements Serializable {
    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("amount")
    private float amount;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("discount_amount")
    private float discountAmount;

    @JsonProperty("transaction_init_id")
    private UUID transactionID;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("gateway_id")
    private UUID gatewayId;

    @JsonProperty("gateway_channel_id")
    private String gatewayChannelId;

    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("voucher_code")
    private String voucherCode;
}
