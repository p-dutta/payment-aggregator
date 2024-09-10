package com.banglalink.toffee.models.dto;

import com.banglalink.toffee.models.enums.PaymentType;
import com.banglalink.toffee.models.enums.GatewayCallbackStatus;
import com.banglalink.toffee.models.enums.GatewayIpnStatus;
import com.banglalink.toffee.models.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto implements Serializable {
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

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @JsonProperty("status")
    private TransactionStatus status;

    @JsonProperty("ipn_status")
    private GatewayIpnStatus ipnStatus;

    @JsonProperty("gateway_callback_status")
    private GatewayCallbackStatus callbackStatus;

    @JsonProperty("transaction_id")
    private UUID transactionId;

    @JsonProperty("transaction_request_id")
    private UUID transactionRequestId;

    @JsonProperty("gateway_transaction_id")
    private String gatewayTransactionId;

    @JsonProperty("voucher_code")
    private String voucherCode;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}

