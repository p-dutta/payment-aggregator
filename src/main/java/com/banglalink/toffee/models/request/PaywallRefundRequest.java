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
public class PaywallRefundRequest {
    @JsonProperty("platform_name")
    private String platformName;

    @JsonProperty("refund_amount")
    private float refundAmount;

    @JsonProperty("reason")
    private String reason;
}
