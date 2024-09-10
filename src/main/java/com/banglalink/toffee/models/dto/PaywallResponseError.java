package com.banglalink.toffee.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaywallResponseError {
    @JsonProperty("statusCode")
    private int code;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private Map<String, List<String>> message;

    @JsonProperty("data")
    @JsonInclude(NON_NULL)
    private Object data;

    public String getMessage() {
        StringJoiner joiner = new StringJoiner("; ");
        for (Map.Entry<String, List<String>> entry : this.message.entrySet()) {
            joiner.add(String.join(",", entry.getValue()));
        }
        return joiner.toString();
    }
}
