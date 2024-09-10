package com.banglalink.toffee.controller;

import com.banglalink.toffee.annotation.AuthHeader;
import com.banglalink.toffee.models.dto.PagedResult;
import com.banglalink.toffee.models.dto.ResponseDto;
import com.banglalink.toffee.models.dto.TransactionCreateDto;
import com.banglalink.toffee.models.dto.TransactionRequestDto;
import com.banglalink.toffee.models.enums.SortOrder;
import com.banglalink.toffee.models.enums.TransactionSearchColumn;
import com.banglalink.toffee.models.enums.TransactionSearchType;
import com.banglalink.toffee.models.enums.TransactionsSortBy;
import com.banglalink.toffee.models.request.RefundTransactionRequest;
import com.banglalink.toffee.models.request.TransactionCreateRequest;
import com.banglalink.toffee.models.request.TransactionInitRequest;
import com.banglalink.toffee.service.PaymentTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

import static com.banglalink.toffee.utils.ResponseBuilder.*;
import static com.banglalink.toffee.utils.ValidationUtil.validateDateRanges;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("${routePrefix}/${apiVersion}")
@RequiredArgsConstructor
public class TransactionController {
    private final PaymentTransactionService transactionService;

    @GetMapping("/transaction/search")
    public ResponseEntity<ResponseDto<PagedResult<?>>> searchTransactions(
            @AuthHeader(value = "decodedPayload.header") String decodedPayload,
            @RequestParam(value = "type", defaultValue = "completed") TransactionSearchType transactionType,
            @RequestParam Map<String, String> queryParams,
            @RequestParam(name = "searchColumn", required = false, defaultValue = "createdAt") TransactionSearchColumn searchColumn,
            @RequestParam(required = false, defaultValue = "createdAt") TransactionsSortBy sortBy,
            @RequestParam(required = false, defaultValue = "asc") SortOrder sortOrder
    ) {
        validateDateRanges(queryParams.get("startDate"), queryParams.get("endDate"));

        PagedResult<?> result = transactionService.searchTransactions(queryParams, searchColumn, sortBy, sortOrder, transactionType);

        return ok(getSuccessResponse(result, "success"));
    }

    @PostMapping("/init")
    public ResponseEntity<ResponseDto<TransactionRequestDto>> initPaymentTransactionRequest(
            @AuthHeader(value = "decodedPayload.header") String decodedPayload,
            @RequestBody @Valid TransactionInitRequest request
    ) {
        TransactionRequestDto transactionRequestDto = transactionService.initPaymentTransactionRequest(decodedPayload, request);

        return ok(initSuccessResponse(transactionRequestDto));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<TransactionCreateDto>> createPaymentTransaction(
            @AuthHeader(value = "decodedPayload.header") String decodedPayload,
            @RequestBody @Valid TransactionCreateRequest request
    ) {
        TransactionCreateDto transactionRequestDto = transactionService
                .createPaymentTransaction(decodedPayload, request);

        return ok(createSuccessResponse(transactionRequestDto));
    }

    @PostMapping("/refund/{transaction_id}")
    public ResponseEntity<ResponseDto<?>> refundTransaction(
            @AuthHeader(value = "decodedPayload.header") String decodedPayload,
            @PathVariable("transaction_id") UUID transactionID,
            @RequestBody @Valid RefundTransactionRequest request
    ) {
        transactionService.refundTransaction(transactionID, request);
        return ok(refundSuccessResponse());
    }

}

