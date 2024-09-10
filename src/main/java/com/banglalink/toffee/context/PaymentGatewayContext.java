package com.banglalink.toffee.context;

import com.banglalink.toffee.models.dto.PaymentGatewayDto;

public class PaymentGatewayContext {
    private static final ThreadLocal<PaymentGatewayDto> context = new ThreadLocal<>();

    public static void set(PaymentGatewayDto paymentGatewayDto) {
        context.set(paymentGatewayDto);
    }

    public static PaymentGatewayDto get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
