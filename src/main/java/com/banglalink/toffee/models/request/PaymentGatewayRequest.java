package com.banglalink.toffee.models.request;

import com.banglalink.toffee.annotation.ValidEnum;
import com.banglalink.toffee.models.dto.GatewaySubChannelDto;
import com.banglalink.toffee.models.enums.GatewayType;
import com.banglalink.toffee.models.enums.PaymentMode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PaymentGatewayRequest extends RequestPayload {
    @JsonProperty("name")
    @NotEmpty(message = "name can't be empty")
    private String name;

    @JsonProperty("description")
    @NotEmpty(message = "description can't be empty")
    private String description;

    @JsonProperty("base_url")
    @NotEmpty(message = "base URL can't be empty")
    private String baseUrl;

    @JsonProperty("image_label")
    private String imageLabel;

    @JsonProperty("supports_partial_payment")
    private Boolean supportsPartialPayment;

    @JsonProperty("supports_recurring_payment")
    private Boolean supportsRecurringPayment;

    @JsonProperty("gateway_type")
    @ValidEnum(enumClass = GatewayType.class, paramName = "gateway_type")
    private String gatewayType;

    @JsonProperty("payment_mode")
    @ValidEnum(enumClass = PaymentMode.class, paramName = "payment_mode")
    private String paymentMode;

    @JsonProperty("status")
    @NotNull(message = "status can't be empty")
    private Boolean status;

    @JsonProperty("operator")
    private String operator;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("blacklisted_countries")
    private List<String> blacklistedCountries;

    @JsonProperty("gateway_sub_channels")
    private List<GatewaySubChannelDto> gatewaySubChannels;

    public String toString() { return new Gson().toJson(this); }
}
