package com.banglalink.toffee.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaywallDirectPayRequest {
    @JsonProperty("platform_name")
    private String platformName;

    @JsonProperty("initiator_email")
    private String initiatorEmail;

    @JsonProperty("initiator_msisdn")
    private String initiatorMsisdn;

    @JsonProperty("ipn_url")
    private String ipnUrl;

    @JsonProperty("sub_channel_id")
    private String subChannelId;

    @JsonProperty("payment_data")
    private List<PaymentData> paymentData;

    @JsonProperty("additional_params")
    private Map<String, Object> additionalParams;

    @JsonProperty("gateway_id")
    private Long gatewayId;
}
