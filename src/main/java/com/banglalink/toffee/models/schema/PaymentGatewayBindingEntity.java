package com.banglalink.toffee.models.schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ConstantUtil.PAYMENT_GATEWAY_BINDINGS_TABLE_NAME)
public class PaymentGatewayBindingEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "gateway_id")
    private UUID gatewayId;

    @Column(name = "requester_id")
    private UUID requesterId;

    @Column(name = "initiator_msisdn", nullable = false)
    private String initiatorMsisdn;

    @Column(name = "mfs_account", nullable = false)
    private String mfsAccount;

    @Column(name = "paywall_gateway_id", nullable = false)
    private Long paywallGatewayId;

    @Column(name = "status")
    private String status;
}