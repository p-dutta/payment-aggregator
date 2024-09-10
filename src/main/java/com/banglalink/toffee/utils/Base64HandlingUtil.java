package com.banglalink.toffee.utils;

public class Base64HandlingUtil {
    public static String verifyBase64Padding(String base64EncodedString) {
        if (base64EncodedString == null) return "";

        int padLength = base64EncodedString.length() % 4;
        StringBuilder sb = new StringBuilder(base64EncodedString);

        if (padLength == 2) {
            sb.append("==");
        } else if (padLength == 3) {
            sb.append("=");
        }

        return sb.toString();
    }
}
