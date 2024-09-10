package com.banglalink.toffee.aspect;

import com.banglalink.toffee.exception.AuthException;
import com.banglalink.toffee.models.schema.PayloadData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Objects;

import static com.banglalink.toffee.models.schema.ConstantUtil.HEADER_DECODING_PROFILE;
import static com.banglalink.toffee.utils.HeaderUtil.getDecodedHeader;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessLevelAspect {
    private final ObjectMapper objectMapper;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${decodedPayload.header}")
    private String authHeaderKey;

    @Value("${app.admin-roles}")
    private List<String> adminRoles;

    @Before("execution(* com.banglalink.toffee.controller.PaymentGatewayController.createPaymentGateway(..)) ||" +
            "execution(* com.banglalink.toffee.controller.PaymentGatewayController.updatePaymentGateway(..)) ||" +
            "execution(* com.banglalink.toffee.controller.PaymentGatewayController.fetchAllGateways(..)) ||" +
            "execution(* com.banglalink.toffee.controller.PaymentGatewayController.fetchGatewayByGatewayId(..)) ||" +
            "execution(* com.banglalink.toffee.controller.PaymentGatewayController.deletePaymentGatewayById(..)) ||" +
            "execution(* com.banglalink.toffee.controller.PaymentGatewayController.getImageUploadUrl(..)) ||" +
            "execution(* com.banglalink.toffee.controller.PaymentGatewayController.fetchGatewayImages(..)) ||" +
            "execution(* com.banglalink.toffee.controller.PaymentGatewayBlacklistingController.*(..)) ||" +
            "execution(* com.banglalink.toffee.controller.TransactionController.searchTransactions(..)) ||" +
            "execution(* com.banglalink.toffee.controller.TransactionController.refundTransaction(..))")
    public void checkAdminAccess(JoinPoint jp) throws JsonProcessingException {
        HttpServletRequest request = Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String jtiClaims = request.getHeader(authHeaderKey);

        if (activeProfile.equals(HEADER_DECODING_PROFILE))
            jtiClaims = getDecodedHeader(jtiClaims);

        PayloadData authData = objectMapper.readValue(jtiClaims, PayloadData.class);
        String clientRole = authData.getType().toLowerCase();

        if (!adminRoles.contains(clientRole.toLowerCase()))
            throw new AuthException(String.format("Access Denied for role '%s'", clientRole));
    }
}
