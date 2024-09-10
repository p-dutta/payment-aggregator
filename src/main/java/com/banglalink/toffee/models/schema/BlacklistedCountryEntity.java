package com.banglalink.toffee.models.schema;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = ConstantUtil.PAYMENT_GATEWAY_BLACKLISTED_COUNTRIES_TABLE_NAME,
        uniqueConstraints = {@UniqueConstraint(columnNames = {"gateway_id", "country_code"})})
public class BlacklistedCountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "country_code")
    private String countryCode;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id")
    private PaymentGatewayEntity paymentGateways;
}
