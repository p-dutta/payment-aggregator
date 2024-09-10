package com.banglalink.toffee.exception.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ApiError {
    private int code;
    private Boolean status;
    private String message;
    private Object data;
}
