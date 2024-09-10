package com.banglalink.toffee.models.schema;

import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
public class ConstantUtil {
    public static final String PAYMENT_GATEWAYS_TABLE_NAME = "gateways";
    public static final String PAYMENT_GATEWAY_IMAGES_TABLE_NAME = "images";
    public static final String PAYMENT_GATEWAY_REFUND_TABLE_NAME = "refund";
    public static final String PAYMENT_GATEWAY_SUB_CHANNELS_TABLE_NAME = "gateway_sub_channels";
    public static final String PAYMENT_GATEWAY_BLACKLISTED_COUNTRIES_TABLE_NAME = "gateway_blacklisted_countries";
    public static final String PAYMENT_GATEWAY_TRANSACTION_REQUESTS_TABLE_NAME = "transaction_requests";
    public static final String PAYMENT_GATEWAY_TRANSACTIONS_TABLE_NAME = "transactions";
    public static final String PAYMENT_GATEWAY_BINDINGS_TABLE_NAME = "gateway_bindings";

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAXIMUM_PAGE_SIZE = 25;
    public static final int PAGE = 0;
    public static final String REQ_LOG_PATTERN = "{method=%s, uri=%s, headers=%s, payload=%s, query_params=%s}";
    public static final String RES_LOG_PATTERN = "{response_data=%s, code=%s}";
    public static final List<String> REQ_LOG_HEADER_PATTERN = List.of("host", "x-decoded-payload", "x-apigateway-api-userinfo", "user-agent");
    public static final String INVALID_ENUM_MESSAGE = "Invalid argument provided for '%s', expected values: [%s]";

    public static final String PAYWALL_PLATFORM = "ToffeeApp";
    public static final String PAYWALL_SUB_CHANNEL_ID = "2"; // static for phase-1. "1" -> recharge, "2" -> transaction
    public static final String PAYWALL_CLIENT_SECRET_KEY = "cl-secret";
    public static final String PAYWALL_CLIENT_ID_KEY = "cl-id";
    public static final long PAYWALL_BKASH_GATEWAY_ID = 502;

    //@Value("${app.paywall.base-url}")
    //public static String PAYWALL_GW_BASE_URL;
    public static final String PAYWALL_INIT_API_PATH = "/v1/init";
    public static final String PAYWALL_DIRECT_PAY_API_PATH = "/v1/direct-pay";
    public static final String PAYWALL_REFUND_API_PATH = "/v1/pwp/refund/";
    public static final String PAYWALL_MFS_BIND_PATH = "/v3/dpwallet/init";
    public static final String PAYWALL_MFS_UNBIND_PATH = "/v3/dpwallet/unbind";
    public static final String PAYWALL_IPN_PATH = "/v1/ipn";
    public static final String HEADER_DECODING_PROFILE = "dev";

    public static final int GENERIC_EXCEPTION_CODE = 5000;
    public static final int AUTH_EXCEPTION_CODE = 5001;
    public static final int BAD_REQUEST_EXCEPTION_CODE = 5002;
    public static final int NOT_FOUND_EXCEPTION_CODE = 5004;
    public static final int INVALID_METHOD_ARGUMENT_CODE = 5010;
    public static final int MISSING_HEADER_EXCEPTION_CODE = 5011;

    public static final String START_OF_TIME = "1970-01-01 00:00:00.000";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static final String IMAGE_UPLOAD_PATH = "/v1/bucket/sign/url";

    public static final String PGW_CACHE_STORE = "paymentGateways";
}
