package com.banglalink.toffee.models.schema;

import com.banglalink.toffee.models.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantUtil.PAYMENT_GATEWAY_TRANSACTION_REQUESTS_TABLE_NAME)
public class PaymentTransactionRequestEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_init_id")
    @UuidGenerator
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

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name="payment_type")
    private PaymentType paymentType;

    @Column(name="voucher_code")
    private String voucherCode;
}