package com.banglalink.toffee.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class PaywallMfsBindingRequest extends RequestPayload {
    @JsonProperty("platform_name")
    private String platformName;

    @JsonProperty("initiator_msisdn")
    private String initiatorMsisdn;

    @JsonProperty("gateway_id")
    private long gatewayId;

    @JsonProperty("redirect_url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String redirectUrl;

    @JsonProperty("additional_params")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> additionalParams;

    public String toString() {
        return new Gson().toJson(this);
    }
}
