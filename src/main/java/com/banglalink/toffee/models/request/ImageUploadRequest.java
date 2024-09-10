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
public class ImageUploadRequest extends RequestPayload {
    @NotEmpty(message = "platform can't be empty")
    @JsonProperty("platform")
    private String platform;

    @NotEmpty(message = "label can't be empty")
    @JsonProperty("label")
    private String label;

    @NotEmpty(message = "file_name can't be empty")
    @JsonProperty("file_name")
    private String fileName;

    public String toString() {
        return new Gson().toJson(this);
    }
}
