package com.banglalink.toffee.models.request;

import com.banglalink.toffee.annotation.ValidEnum;
import com.banglalink.toffee.models.enums.GatewayIpnStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Builder
@Data
public class PaywallIpnRequest extends RequestPayload {
    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("initiator_email")
    private String initiatorEmail;

    @JsonProperty("initiator_msisdn")
    private String initiatorMsisdn;

    @JsonProperty("payment_status")
    @ValidEnum(enumClass = GatewayIpnStatus.class, paramName = "payment_status")
    private String paymentStatus;

    @JsonProperty("recharge_status")
    private String rechargeStatus;

    @JsonProperty("payment_amount")
    private double paymentAmount;

    @JsonProperty("payment_currency")
    private String paymentCurrency;

    @JsonProperty("payment_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String paymentDate;

    @JsonProperty("additional_params")
    private Map<String, Object> additionalParams;

    public String toString() {
        return new Gson().toJson(this);
    }
}
