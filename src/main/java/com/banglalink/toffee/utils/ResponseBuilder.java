package com.banglalink.toffee.utils;

import com.banglalink.toffee.models.dto.ResponseDto;
import org.springframework.http.HttpStatus;

import static com.banglalink.toffee.utils.LoggingService.logResponse;

public class ResponseBuilder {
    public static <T> ResponseDto<T> getResponse(T entity, int status, boolean successStatus, String message){
        logResponse(entity, status);
        return ResponseDto.<T>builder()
                .message(message)
                .success(successStatus)
                .data(entity)
                .build();
    }

    public static <T> ResponseDto<T> getSuccessResponse(T entity, String msg) {
        return getResponse(entity, HttpStatus.OK.value(), true, msg);
    }

    public static <T> ResponseDto<T> createSuccessResponse(T entity) {
        return getResponse(entity, HttpStatus.OK.value(), true, "Successfully created payment request");
    }

    public static <T> ResponseDto<T> initSuccessResponse(T entity) {
        return getResponse(entity, HttpStatus.OK.value(), true, "Initialized payment request");
    }

    public static ResponseDto<?> refundSuccessResponse() {
        return ResponseDto.builder()
                .message("Refund has been processed successfully")
                .success(true)
                .build();
    }

}
