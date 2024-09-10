package com.banglalink.toffee.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class GatewayMfsBindingDto implements Serializable {
    @JsonProperty("web_url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String webUrl;
}
