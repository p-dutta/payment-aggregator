package com.banglalink.toffee.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GatewayBindingDto implements Serializable {
    @JsonProperty("initiator_msisdn")
    private String initiatorMsisdn;

    @JsonProperty("mfs_account")
    private String mfsAccount;

    @JsonProperty("paywall_gateway_id")
    private Long paywallGatewayId;

    @JsonProperty("gateway_id")
    private UUID gatewayId;

    @JsonProperty("requester_id")
    private UUID requesterId;

    @JsonProperty("status")
    private String status;
}
