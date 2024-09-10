package com.banglalink.toffee.models.schema;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JtiData {
    private String country;

    private UUID device_id;

    private String provider;

    private UUID requester_id;
}
