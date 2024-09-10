package com.banglalink.toffee.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@Accessors(chain = true)
public class BlacklistCoutriesRequest extends RequestPayload {
    @JsonProperty("gateway_id")
    private UUID gatewayId;

    @JsonProperty("country_code")
    private List<String> countryCode;

    public String toString() {
        return new Gson().toJson(this);
    }
}
