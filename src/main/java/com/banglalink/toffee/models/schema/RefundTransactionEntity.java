package com.banglalink.toffee.models.schema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = ConstantUtil.PAYMENT_GATEWAY_REFUND_TABLE_NAME)
public class RefundTransactionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "charge")
    private String charge;

    @Column(name = "additional_id")
    private String additionalId;

    @Column(name = "third_party_tid")
    private String thirdPartyTid;

    @Column(name= "refund_amount")
    private float refundAmount;

    @Column(name = "status")
    private String status;

    @Column(name = "transaction_id")
    private UUID transactionId;
}
