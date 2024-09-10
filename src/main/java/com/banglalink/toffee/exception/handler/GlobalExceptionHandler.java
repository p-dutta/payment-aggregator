package com.banglalink.toffee.exception.handler;

import com.banglalink.toffee.exception.AuthException;
import com.banglalink.toffee.exception.BadRequestException;
import com.banglalink.toffee.exception.RecordNotFoundException;
import com.banglalink.toffee.exception.model.ApiError;
import com.banglalink.toffee.models.schema.ConstantUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

import static com.banglalink.toffee.models.schema.ConstantUtil.INVALID_ENUM_MESSAGE;
import static com.banglalink.toffee.models.schema.ConstantUtil.INVALID_METHOD_ARGUMENT_CODE;
import static com.banglalink.toffee.utils.ValidationUtil.getEnumConstants;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestHandler(BadRequestException e) {
        log.error(e.getMessage(), e);
        return getErrorResponse(ApiError.builder()
                .code(ConstantUtil.BAD_REQUEST_EXCEPTION_CODE)
                .status(false)
                .message(e.getMessage())
                .data(new HashMap<>())
                .build());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> recordNotFoundExceptionHandler(RecordNotFoundException e) {
        log.error(e.getMessage(), e);
        return getErrorResponse(ApiError.builder()
                .code(ConstantUtil.NOT_FOUND_EXCEPTION_CODE)
                .status(false)
                .message(e.getMessage())
                .data(new HashMap<>())
                .build());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> authExceptionHandler(AuthException e) {
        log.error(e.getMessage(), e);
        return getErrorResponse(ApiError.builder()
                .code(ConstantUtil.AUTH_EXCEPTION_CODE)
                .status(false)
                .message(e.getMessage())
                .data(new HashMap<>())
                .build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        String message = e.getMessage();
        Class<?> type = e.getParameter().getParameterType();

        if (e.getParameter().getParameterType().isEnum()) {
            Class<? extends Enum<?>> enumType = (Class<? extends Enum<?>>) type;
            String expectedValues = getEnumConstants(enumType);
            message = String.format(INVALID_ENUM_MESSAGE, e.getName(), expectedValues);
        }

        return getErrorResponse(ApiError.builder()
                .code(INVALID_METHOD_ARGUMENT_CODE)
                .status(false)
                .message(message)
                .data(new HashMap<>())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentConstraintException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ApiError.builder()
                .code(INVALID_METHOD_ARGUMENT_CODE)
                .status(false)
                .message(message)
                .data(new HashMap<>())
                .build());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ApiError.builder()
                .code(ConstantUtil.MISSING_HEADER_EXCEPTION_CODE)
                .status(false)
                .message(ex.getLocalizedMessage())
                .data(new HashMap<>())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e) {
        log.error(e.getMessage(), e);
        return getErrorResponse(ApiError.builder()
                .code(ConstantUtil.GENERIC_EXCEPTION_CODE)
                .status(false)
                .message(e.getMessage())
                .data(new HashMap<>())
                .build());
    }

    private ResponseEntity<ApiError> getErrorResponse(ApiError apiError) {
        return ResponseEntity
                .status(getStatusCode(apiError.getCode()))
                .body(apiError);
    }

    private HttpStatus getStatusCode(int customResponseCode) {
        switch (customResponseCode) {
            case ConstantUtil.AUTH_EXCEPTION_CODE:
                return HttpStatus.UNAUTHORIZED;
            case ConstantUtil.NOT_FOUND_EXCEPTION_CODE:
                return HttpStatus.NOT_FOUND;
            case ConstantUtil.BAD_REQUEST_EXCEPTION_CODE:
                return HttpStatus.BAD_REQUEST;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}