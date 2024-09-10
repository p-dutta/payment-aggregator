package com.banglalink.toffee.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaywallMfsBindingResponse {

    @JsonProperty("statusCode")
    private int code;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private ResponseData data;

    @Data
    public static class ResponseData {
        @JsonProperty("weburl")
        private String webUrl;

        @JsonProperty("result_status")
        private String resultStatus;

        @JsonProperty("result_message")
        private String resultMessage;
    }

    public String toString() { return new Gson().toJson(this); }
}
