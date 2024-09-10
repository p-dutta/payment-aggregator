package com.banglalink.toffee.models.request;

import com.banglalink.toffee.annotation.ValidEnum;
import com.banglalink.toffee.models.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInitRequest extends RequestPayload {
    @PositiveOrZero(message = "amount must be greater than or equal to 0")
    @JsonProperty("amount")
    private float amount;

    @PositiveOrZero(message = "discount_amount must be greater than or equal to 0")
    @JsonProperty("discount_amount")
    private float discountAmount;

    @NotEmpty(message = "msisdn/email can't be empty")
    @JsonProperty("msisdn")
    private String msisdn;

    @NotEmpty(message = "product_id can't be empty")
    @JsonProperty("product_id")
    private String productId;

    @NotNull(message = "gateway_id can't be empty")
    @JsonProperty("gateway_id")
    private UUID gatewayId;

    @NotEmpty(message = "gateway_channel_id can't be empty")
    @JsonProperty("gateway_channel_id")
    private String gatewayChannelId;

    @JsonProperty("payment_type")
    @NotEmpty(message = "payment_type can't be empty")
    @ValidEnum(enumClass = PaymentType.class, paramName = "payment_type")
    private String paymentType;

    @JsonProperty("voucher_code")
    private String voucherCode;

    public String toString() {
        return new Gson().toJson(this);
    }
}
