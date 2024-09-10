package com.banglalink.toffee.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaywallInitRequest {
    @JsonProperty("platform_name")
    private String platformName;

    @JsonProperty("initiator_email")
    private String initiatorEmail;

    @JsonProperty("initiator_msisdn")
    private String initiatorMsisdn;

    @JsonProperty("callback_url_success")
    private String successUrl;

    @JsonProperty("callback_url_fail")
    private String failUrl;

    @JsonProperty("callback_url_cancel")
    private String cancelUrl;

    @JsonProperty("ipn_url")
    private String ipnUrl;

    @JsonProperty("sub_channel_id")
    private String subChannelId;

    @JsonProperty("payment_data")
    private List<PaymentData> paymentData;

    @JsonProperty("additional_params")
    private Map<String, Object> additionalParams;
}
