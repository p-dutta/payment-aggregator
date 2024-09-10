package com.banglalink.toffee.models.request;

import com.banglalink.toffee.annotation.ValidEnum;
import com.banglalink.toffee.models.enums.MfsBindingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class PaywallBindingIpnRequest extends RequestPayload {
    @JsonProperty("initiator_msisdn")
    private String initiatorMsisdn;

    @JsonProperty("payment_gateway_account")
    private String paymentGatewayAccount;

    @JsonProperty("payment_gateway_used")
    private String paymentGatewayUsed;

    @JsonProperty("status")
    @ValidEnum(enumClass = MfsBindingStatus.class, paramName = "status")
    private String status;

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String date;

    @JsonProperty("additional_params")
    private Map<String, Object> additionalParams;

    public String toString() {
        return new Gson().toJson(this);
    }
}
