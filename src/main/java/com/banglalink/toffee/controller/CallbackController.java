package com.banglalink.toffee.controller;

import com.banglalink.toffee.models.dto.ResponseDto;
import com.banglalink.toffee.models.request.PaywallBindingIpnRequest;
import com.banglalink.toffee.models.request.PaywallIpnRequest;
import com.banglalink.toffee.service.PaymentGatewayBindingService;
import com.banglalink.toffee.service.PaywallTransactionIpnHandlerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.banglalink.toffee.utils.ResponseBuilder.getSuccessResponse;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("${routePrefix}/${apiVersion}")
@RequiredArgsConstructor
public class CallbackController {
    private final PaywallTransactionIpnHandlerService transactionIpnHandler;

    private final PaymentGatewayBindingService bindingService;

    @PostMapping("/ipn")
    public ResponseEntity<ResponseDto<?>> ipn(
            @RequestBody @Valid PaywallIpnRequest request
    ) {
        transactionIpnHandler.handleIpnResult(request);

        return ok(getSuccessResponse(new HashMap<>(), "successful"));
    }

    @PostMapping("/bind-callback")
    public ResponseEntity<ResponseDto<?>> mfsBind(
            @RequestBody @Valid PaywallBindingIpnRequest bindingIpnRequest
    ) {
        bindingService.handleMfsBindResult(bindingIpnRequest);

        return ok(getSuccessResponse(new HashMap<>(), "successful"));
    }
}