package com.banglalink.toffee.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

import static com.banglalink.toffee.utils.Base64HandlingUtil.verifyBase64Padding;

@Slf4j
public class HeaderUtil {

    public static String getDecodedHeader(String base64EncodedHeader) {
        String paddedBase64Header = verifyBase64Padding(base64EncodedHeader);
        byte[] decodedBytes = Base64.getDecoder().decode(paddedBase64Header);
        return new String(decodedBytes);
    }
}
