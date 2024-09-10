package com.banglalink.toffee.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistedCountriesDto {

    @JsonProperty("id")
    private int id;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("gateway_id")
    private UUID gatewayId;
}
