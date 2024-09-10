package com.banglalink.toffee.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentData {
    @JsonProperty("cashback_amount")
    private Integer cashbackAmount;

    @JsonProperty("cashback_campaign_id")
    private String cashbackCampaignId;

    @JsonProperty("target_msisdn")
    private String targetMsisdn;

    @JsonProperty("amount")
    private float amount;

    @JsonProperty("product_code")
    private String productCode;

    @JsonProperty("details_breakdown")
    private Map<String, Object> detailsBreakdown;
}