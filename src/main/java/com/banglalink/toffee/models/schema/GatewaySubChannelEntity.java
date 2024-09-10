package com.banglalink.toffee.models.schema;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = ConstantUtil.PAYMENT_GATEWAY_SUB_CHANNELS_TABLE_NAME)
public class GatewaySubChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "op_type")
    private String operationType;

    @Column(name = "capability")
    private String capability;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(name="provider")
    private String provider;

    @Column(name="sub_channel_id", nullable = false)
    private String subChannelId;

    @Column(name="external_gateway_id", nullable = false)
    private String externalGatewayId;

    @ManyToOne
    @JoinColumn(name = "gateway_id")
    private PaymentGatewayEntity paymentGateways;
}
