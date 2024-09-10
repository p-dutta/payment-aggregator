package com.banglalink.toffee.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder
@Data
@Accessors(chain = true)
public class ImageUploadServiceRequest extends RequestPayload {
    @JsonProperty("provider")
    private String provider;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("label")
    private String label;

    @JsonProperty("file_name")
    private String fileName;
}
