package com.banglalink.toffee.controller;

import com.banglalink.toffee.annotation.AuthHeader;
import com.banglalink.toffee.models.dto.*;
import com.banglalink.toffee.models.request.ImageUploadRequest;
import com.banglalink.toffee.models.request.PaymentGatewayRequest;
import com.banglalink.toffee.service.GatewayImageUploaderService;
import com.banglalink.toffee.service.PaymentGatewayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.banglalink.toffee.utils.ResponseBuilder.getSuccessResponse;
import static com.banglalink.toffee.utils.ValidationUtil.throwRequestArgumentValidationException;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("${routePrefix}/${apiVersion}")
@RequiredArgsConstructor
public class PaymentGatewayController {

    private final PaymentGatewayService paymentGatewayService;

    private final GatewayImageUploaderService imageUploaderService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<PagedResult<PaymentGatewayDto>>> fetchAllGatewaysForClientApp(
            @AuthHeader(value = "decodedPayload.header") String decodedPayload,
            @RequestParam(value = "limit", required = false) final Integer pageSize,
            @RequestParam(value = "offset", required = false) final Integer page,
            @RequestParam Map<String, String> queryParams
    ) {
        PagedResult<PaymentGatewayDto> pagedResult = paymentGatewayService
                .getAllPageablePaymentGateways(decodedPayload, pageSize, page, queryParams, true);

        return ok(getSuccessResponse(pagedResult, "List of payment gateways fetched"));
    }

    @PostMapping("/gateway")
    public ResponseEntity<ResponseDto<?>> createPaymentGateway(@AuthHeader(value = "decodedPayload.header") String decodedPayload,
                                                               @RequestBody @Valid PaymentGatewayRequest gatewayRequest,
                                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throwRequestArgumentValidationException(bindingResult);

        PaymentGatewayDto result = paymentGatewayService.createPaymentGateway(gatewayRequest);

        return ok(getSuccessResponse(result, "Gateway created successfully"));
    }

    @PutMapping("/gateway/{gatewayId}")
    public ResponseEntity<ResponseDto<PaymentGatewayDto>> updatePaymentGateway(@AuthHeader(value = "decodedPayload.header") String decodedPayload,
                                                               @PathVariable("gatewayId") UUID paymentGatewayId,
                                                               @RequestBody @Valid PaymentGatewayRequest gatewayRequest,
                                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throwRequestArgumentValidationException(bindingResult);

        PaymentGatewayDto result = paymentGatewayService.updatePaymentGateway(gatewayRequest, paymentGatewayId);

        return ok(getSuccessResponse(result, "Gateway updated successfully"));
    }

    @GetMapping("/gateway")
    public ResponseEntity<ResponseDto<PagedResult<PaymentGatewayDto>>> fetchAllGateways(@AuthHeader(value = "decodedPayload.header") String decodedPayload,
                                                                                        @RequestParam(value = "limit", required = false) final Integer pageSize,
                                                                                        @RequestParam(value = "offset", required = false) final Integer page,
                                                                                        @RequestParam Map<String, String> queryParams) {
        PagedResult<PaymentGatewayDto> pagedResult = paymentGatewayService
                .getAllPageablePaymentGateways(decodedPayload, pageSize, page, queryParams, false);

        return ok(getSuccessResponse(pagedResult, "success"));
    }

    @GetMapping("/gateway/{gatewayId}")
    public ResponseEntity<ResponseDto<PaymentGatewayDto>> fetchGatewayByGatewayId(@AuthHeader(value = "decodedPayload.header") String decodedPayload,
                                                                                  @PathVariable("gatewayId") UUID paymentGatewayId) {

        PaymentGatewayDto paymentGatewayDto = paymentGatewayService.getPaymentGatewayById(paymentGatewayId);

        return ok(getSuccessResponse(paymentGatewayDto, "success"));
    }

    @DeleteMapping("/gateway/{gatewayId}")
    public ResponseEntity<ResponseDto<?>> deletePaymentGatewayById(@AuthHeader(value = "decodedPayload.header") String decodedPayload,
                                                                   @PathVariable("gatewayId") UUID paymentGatewayId) {
        paymentGatewayService.deletePaymentGatewayById(paymentGatewayId);

        return ok(getSuccessResponse(new HashMap(), "Gateway deleted successfully"));
    }

    @PostMapping("/image")
    public ResponseEntity<ResponseDto<GatewayImageResponseDto>> getImageUploadUrl(@AuthHeader(value = "decodedPayload.header") String decodedPayload,
                                                                                  @RequestBody @Valid ImageUploadRequest uploadRequest,
                                                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throwRequestArgumentValidationException(bindingResult);

        GatewayImageResponseDto response = imageUploaderService.prepareImageUploadUrlFromGCP(uploadRequest);

        return ok(getSuccessResponse(response, "success"));
    }

    @GetMapping("/image")
    public ResponseEntity<ResponseDto<List<ImageDto>>> fetchGatewayImages(@AuthHeader(value = "decodedPayload.header") String decodedPayload,
                                                                          @RequestParam Map<String, String> queryParams) {
        List<ImageDto> imagesList = paymentGatewayService.fetchGatewayImages(queryParams);

        return ok(getSuccessResponse(imagesList, "success"));
    }
}
