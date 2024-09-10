package com.banglalink.toffee.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class PaywallResponse {

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
        @JsonProperty("transaction_id")
        private String transactionId;

        @JsonProperty("weburl")
        private String webUrl;

        @JsonProperty("payment_create_time")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private String paymentCreateTime;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("status")
        private String status;
    }

    public String toString() { return new Gson().toJson(this); }
}
