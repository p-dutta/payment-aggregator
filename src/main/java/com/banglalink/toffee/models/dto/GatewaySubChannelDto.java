package com.banglalink.toffee.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GatewaySubChannelDto implements Serializable {
    @JsonProperty("op_type")
    private String operationType;

    @JsonProperty("capability")
    private String capability;

    @JsonProperty("base_url")
    private String baseUrl;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("external_gateway_id")
    private String externalGatewayId;

    @JsonProperty("sub_channel_id")
    private String subChannelId;
}
