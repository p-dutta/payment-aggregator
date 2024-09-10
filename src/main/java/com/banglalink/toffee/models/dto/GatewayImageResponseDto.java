package com.banglalink.toffee.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GatewayImageResponseDto {
    @JsonProperty("read_url")
    private String readUrl;

    @JsonProperty("write_url")
    private String writeUrl;
}
