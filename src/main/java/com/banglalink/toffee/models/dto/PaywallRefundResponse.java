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
public class PaywallRefundResponse {

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
        @JsonProperty("charge")
        private String charge;

        @JsonProperty("response_id")
        private String responseId;

        @JsonProperty("payment_id")
        private String paymentId;

        @JsonProperty("refund_amount")
        private float refundAmount;
    }

    public String toString() { return new Gson().toJson(this); }
}
