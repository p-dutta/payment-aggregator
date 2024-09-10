package com.banglalink.toffee.controller;

import com.banglalink.toffee.annotation.AuthHeader;
import com.banglalink.toffee.models.dto.DirectPayDto;
import com.banglalink.toffee.models.dto.GatewayMfsBindingDto;
import com.banglalink.toffee.models.dto.ResponseDto;
import com.banglalink.toffee.models.request.MfsBindingRequest;
import com.banglalink.toffee.service.PaymentGatewayBindingService;
import com.banglalink.toffee.service.PaymentGatewayService;
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
public class MfsGatewayController {
    private final PaymentGatewayService paymentGatewayService;
    private final PaymentGatewayBindingService bindingService;

    @PostMapping("/mfs/bind")
    public ResponseEntity<ResponseDto<GatewayMfsBindingDto>> bindMfsGateway(@AuthHeader(value = "decodedPayload.header") String decodedPayload,
                                                         @RequestBody @Valid MfsBindingRequest bindingRequest) {
        GatewayMfsBindingDto response = bindingService.bindMfsGateway(decodedPayload, bindingRequest);

        return ok(getSuccessResponse(response, "success"));
    }

    @PostMapping("/mfs/unbind")
    public ResponseEntity<ResponseDto<?>> unbindMfsGateway(@AuthHeader(value = "decodedPayload.header") String decodedPayload,
                                                         @RequestBody @Valid MfsBindingRequest bindingRequest) {
        bindingService.unbindMfsGateway(decodedPayload, bindingRequest);

        return ok(getSuccessResponse(new HashMap<>(), "success"));
    }

    @GetMapping("/check-binding") // direct-debit bindings
    public ResponseEntity<ResponseDto<DirectPayDto>> fetchDirectPayGateway(@AuthHeader(value = "decodedPayload.header") String decodedPayload) {
        DirectPayDto directPayDto = paymentGatewayService.getDirectPayEnableInfo(decodedPayload);

        return ok(getSuccessResponse(directPayDto, "success"));
    }
}
