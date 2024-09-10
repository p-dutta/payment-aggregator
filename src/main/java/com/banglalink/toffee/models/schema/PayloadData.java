package com.banglalink.toffee.models.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PayloadData {
    private String aud;
    private String country;
    private long exp;
    private long iat;
    private String iss;
    private String jti;
    private String provider;
    private String token;
    private String type;
}
