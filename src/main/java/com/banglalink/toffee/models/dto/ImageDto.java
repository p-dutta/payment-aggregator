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
public class ImageDto implements Serializable {
    @JsonProperty("slug")
    private String slug;

    @JsonProperty("read_url")
    private String readUrl;

    @JsonProperty("write_url")
    private String writeUrl;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("provider")
    private String provider;
}
