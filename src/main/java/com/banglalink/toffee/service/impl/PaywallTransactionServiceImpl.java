package com.banglalink.toffee.service.impl;

import com.banglalink.toffee.context.PaymentGatewayContext;
import com.banglalink.toffee.exception.BadRequestException;
import com.banglalink.toffee.models.dto.*;
import com.banglalink.toffee.models.enums.PaywallRequestType;
import com.banglalink.toffee.models.request.MfsBindingRequest;
import com.banglalink.toffee.models.request.RefundTransactionRequest;
import com.banglalink.toffee.models.request.TransactionCreateRequest;
import com.banglalink.toffee.models.schema.JtiData;
import com.banglalink.toffee.models.schema.TransactionModel;
import com.banglalink.toffee.service.PaywallTransactionService;
import com.banglalink.toffee.service.mapper.PaywallRequestMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.BiFunction;

import static com.banglalink.toffee.models.enums.PaywallRequestType.*;
import static com.banglalink.toffee.models.schema.ConstantUtil.*;

@Slf4j
@Service
public class PaywallTransactionServiceImpl implements PaywallTransactionService {
    private final PaywallRequestMapper requestMapper;
    private final Map<PaywallRequestType, BiFunction<Object, Object, RequestData>> handler;
    private final ObjectMapper objectMapper;

    @Qualifier("payWallWebClient")
    private final WebClient client;

    @Autowired
    public PaywallTransactionServiceImpl(PaywallRequestMapper requestMapper, @Qualifier("payWallWebClient") WebClient webclient, ObjectMapper objectMapper) {
        this.requestMapper = requestMapper;
        this.client = webclient;
        this.objectMapper = objectMapper;
        this.handler = Map.of(
                INITIALIZE, this::initContractData,
                DIRECT_PAY, this::directPayData,
                REFUND, this::refundData,
                MFS_BIND, this::bindMfsData,
                MFS_UNBIND, this::unbindMfsData
        );
    }

    @Override
    public <T, DT> Object handlePaywallOperation(T request, DT dto, PaywallRequestType requestType) {
        RequestData requestData = mapAttributesForPaywallOperations(request, dto, requestType);

        log.info("PAYWALL REQUEST: [POST] {}{}, body: {}", requestData.baseUrl(), requestData.apiPath(), requestData.payload());
        // customize webclient if needed
        return client.mutate()
                .baseUrl(requestData.baseUrl()).build()
                .post()
                .uri(requestData.apiPath())
                .body(BodyInserters.fromValue(requestData.payload()))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> handleError(clientResponse.bodyToMono(PaywallResponseError.class)))
                .bodyToMono(requestData.responseType())
                .onErrorResume(throwable -> Mono.error(new RuntimeException(throwable.getMessage())))
                .doOnSuccess(res -> log.info("PAYWALL RESPONSE: {}", res))
                .block();
    }

    private Mono<Throwable> handleError(Mono<PaywallResponseError> errorMono) {
        return errorMono.flatMap(clientError -> {
            log.error("PAYWALL ERR: {}", clientError.getMessage());
            return Mono.error(new RuntimeException(clientError.getMessage()));
        });
    }

    private <T, DT> RequestData mapAttributesForPaywallOperations(T request, DT dto, PaywallRequestType requestType) {
        BiFunction<Object, Object, RequestData> handler = this.handler.get(requestType);
        if (handler == null)
            throw new IllegalArgumentException(String.format("Unsupported request type: %s", requestType));

        return handler.apply(request, dto);
    }

    private RequestData directPayData(Object transactionRequest, Object dto) {
        PaymentGatewayDto paymentGateway = PaymentGatewayContext.get();
        TransactionModel transactionModel = extractRequestModel(dto);
        TransactionCreateRequest transactionCreateRequest = extractRequestModel(transactionRequest);

        String payload = requestMapper.preparePayloadForDirectPay(transactionModel, transactionCreateRequest);
        String baseUrl = extractBaseUrlFromGateway(paymentGateway, transactionCreateRequest.getGatewayChannelId());
        return new RequestData(baseUrl, PAYWALL_DIRECT_PAY_API_PATH, payload, PaywallResponse.class);
    }

    private RequestData initContractData(Object transactionRequest, Object dto) {
        PaymentGatewayDto paymentGateway = PaymentGatewayContext.get();
        TransactionModel transactionModel = extractRequestModel(dto);
        TransactionCreateRequest transactionCreateRequest = extractRequestModel(transactionRequest);

        String payload = requestMapper.preparePayloadForInitializingContract(transactionModel, transactionCreateRequest);
        String baseUrl = extractBaseUrlFromGateway(paymentGateway, transactionCreateRequest.getGatewayChannelId());
        return new RequestData(baseUrl, PAYWALL_INIT_API_PATH, payload, PaywallResponse.class);
    }

    private RequestData refundData(Object transactionRequest, Object dto) {
        PaymentGatewayDto paymentGateway = PaymentGatewayContext.get();
        TransactionDto transactionDto = extractRequestModel(dto);
        RefundTransactionRequest refundRequest = extractRequestModel(transactionRequest);

        String apiPath = PAYWALL_REFUND_API_PATH + transactionDto.getTransactionId();
        String payload = requestMapper.preparePayloadForRefundTransaction(refundRequest);
        String baseUrl = extractBaseUrlFromGateway(paymentGateway, transactionDto.getGatewayChannelId());
        return new RequestData(baseUrl, apiPath, payload, PaywallRefundResponse.class);
    }

    private RequestData bindMfsData(Object bindingRequest, Object jtiData) {
        PaymentGatewayDto paymentGateway = PaymentGatewayContext.get();
        MfsBindingRequest mfsBindingRequest = extractRequestModel(bindingRequest);

        String baseUrl = extractBaseUrlFromGateway(paymentGateway, mfsBindingRequest.getGatewayChannelId());
        String payload = requestMapper.preparePayloadForMfsBind((MfsBindingRequest) bindingRequest, (JtiData) jtiData, MFS_BIND);
        return new RequestData(baseUrl, PAYWALL_MFS_BIND_PATH, payload, PaywallMfsBindingResponse.class);
    }

    private RequestData unbindMfsData(Object bindingRequest, Object jtiData) {
        PaymentGatewayDto paymentGateway = PaymentGatewayContext.get();
        MfsBindingRequest mfsBindingRequest = extractRequestModel(bindingRequest);

        String baseUrl = extractBaseUrlFromGateway(paymentGateway, mfsBindingRequest.getGatewayChannelId());
        String payload = requestMapper.preparePayloadForMfsBind((MfsBindingRequest) bindingRequest, (JtiData) jtiData, MFS_UNBIND);
        return new RequestData(baseUrl, PAYWALL_MFS_UNBIND_PATH, payload, PaywallMfsBindingResponse.class);
    }

    private String extractBaseUrlFromGateway(PaymentGatewayDto gateway, String subChannelId) {
        var subChannelContext = gateway.getSubChannelDtos().stream().filter(chan -> chan.getSubChannelId().equals(subChannelId)).findFirst().orElse(null);
        GatewaySubChannelDto subChannel =  objectMapper.convertValue(subChannelContext, GatewaySubChannelDto.class);
        if (StringUtils.isEmpty(subChannel.getBaseUrl())) throw new BadRequestException("Provided gateway channel for this operation is misconfigured!");

        return subChannel.getBaseUrl();
    }

    private <T> T extractRequestModel(Object object) {
        return (T) object;
    }

    private record RequestData(String baseUrl, String apiPath, String payload, Class<?> responseType) {
    }
}
