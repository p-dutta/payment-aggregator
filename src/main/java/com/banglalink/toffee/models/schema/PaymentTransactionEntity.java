package com.banglalink.toffee.models.schema;

import com.banglalink.toffee.models.enums.PaymentType;
import com.banglalink.toffee.models.enums.GatewayCallbackStatus;
import com.banglalink.toffee.models.enums.GatewayIpnStatus;
import com.banglalink.toffee.models.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantUtil. PAYMENT_GATEWAY_TRANSACTIONS_TABLE_NAME)
public class PaymentTransactionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_id")
    @UuidGenerator
    private UUID transactionId;

    @Column(name = "transaction_request_id")
    private UUID transactionRequestId;

    @Column(name = "amount")
    private float amount;

    @Column(name = "discount_amount")
    private float discountAmount;

    @Column(name = "gateway_id")
    private UUID gatewayId;

    @Column(name = "gateway_channel_id")
    private String gatewayChannelId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "device_id")
    private UUID deviceId;

    @Column(name = "requester_id")
    private UUID requesterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "gateway_callback_status")
    private GatewayCallbackStatus callbackStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "ipn_status")
    private GatewayIpnStatus ipnStatus;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;

    @Column(name="payment_type")
    private PaymentType paymentType;

    @Column(name="voucher_code")
    private String voucherCode;
}

