package com.banglalink.toffee.models.schema;
import com.banglalink.toffee.models.enums.PaymentType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionModel {
    private float amount;
    private float discountAmount;
    private UUID gatewayId;
    private String gatewayChannelId;
    private String productId;
    private String msisdn;
    private UUID deviceId;
    private UUID requesterId;
    private PaymentType paymentType;
    private String voucherCode;
}
