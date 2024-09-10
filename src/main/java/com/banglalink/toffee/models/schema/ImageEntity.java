package com.banglalink.toffee.models.schema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = ConstantUtil.PAYMENT_GATEWAY_IMAGES_TABLE_NAME)
public class ImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "slug")
    private String slug;

    @Column(name = "read_url", columnDefinition = "TEXT")
    private String readUrl;

    @Column(name = "write_url", columnDefinition = "TEXT")
    private String writeUrl;

    @Column(name= "platform")
    private String platform;

    @Column(name = "provider")
    private String provider;
}
