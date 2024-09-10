package com.banglalink.toffee.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefundTransactionRequest extends RequestPayload {
    @PositiveOrZero(message = "amount must be greater than or equal to 0")
    @JsonProperty("amount")
    private float amount;

    @NotEmpty(message = "reason can't be empty")
    @JsonProperty("reason")
    private String reason;
}
