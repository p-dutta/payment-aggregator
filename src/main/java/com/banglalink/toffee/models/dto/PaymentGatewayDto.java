package com.banglalink.toffee.models.dto;

import com.banglalink.toffee.models.enums.PaymentMode;
import com.banglalink.toffee.models.enums.GatewayType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentGatewayDto implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("gateway_id")
    private UUID gatewayId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("base_url")
    private String baseUrl;

    @JsonProperty("payment_mode")
    private PaymentMode paymentMode;

    @JsonProperty("gateway_type")
    private GatewayType gatewayType;

    @JsonProperty("operator")
    private String operator;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("image_label")
    private String imageLabel;

    @JsonProperty("supports_partial_payment")
    private Boolean supportsPartialPayment;

    @JsonProperty("supports_recurring_payment")
    private Boolean supportsRecurringPayment;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("status")
    private Boolean status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("blacklisted_countries")
    private List<String> blacklistedCountries;

    @JsonProperty("gateway_sub_channels")
    private List<GatewaySubChannelDto> subChannelDtos;

    @JsonProperty("images")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ImageDto> imageDtos;
}
