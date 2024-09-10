package com.banglalink.toffee.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageUploadServiceResponseDto {
    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    @JsonInclude(NON_NULL)
    private ResponseData data;

    @Data
    public static class ResponseData {
        @JsonProperty("write_signed_url")
        private String writeSignedUrl;

        @JsonProperty("read_signed_url")
        private String readSignedUrl;
    }

    public String toString() { return new Gson().toJson(this); }
}
