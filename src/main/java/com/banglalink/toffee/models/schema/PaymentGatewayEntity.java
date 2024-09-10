package com.banglalink.toffee.models.schema;

import com.banglalink.toffee.models.enums.PaymentMode;
import com.banglalink.toffee.models.enums.GatewayType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ConstantUtil.PAYMENT_GATEWAYS_TABLE_NAME)
public class PaymentGatewayEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "base_url", nullable = false)
    private String baseUrl;

    @Column(name = "image_label")
    private String imageLabel;

    @Column(name = "supports_partial_payment")
    private Boolean partialPayment = Boolean.FALSE;

    @Column(name = "supports_recurring_payment")
    private Boolean recurringPayment = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Column(name = "gateway_type")
    private GatewayType gatewayType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private PaymentMode paymentMode;

    @Column(name="provider")
    private String provider;

    @Column(name="operator")
    private String operator;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "paymentGateways")
    private Collection<BlacklistedCountryEntity> gatewayBlacklistedCountry = new HashSet<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "paymentGateways")
    private Collection<GatewaySubChannelEntity> gatewaySubChannels = new HashSet<>();

}
