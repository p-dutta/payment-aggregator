package com.banglalink.toffee.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MfsBindingRequest extends RequestPayload {
    @NotNull(message = "gateway_id can't be empty")
    @JsonProperty("gateway_id")
    private UUID gatewayId;

    @NotEmpty(message = "gateway_channel_id can't be empty")
    @JsonProperty("gateway_channel_id")
    private String gatewayChannelId;

    @NotEmpty(message = "Initiator msisdn or email can't be empty")
    @JsonProperty("initiator_msisdn")
    private String initiatorMsisdn;

    public String toString() {
        return new Gson().toJson(this);
    }
}
