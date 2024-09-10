package com.banglalink.toffee.utils;

import com.banglalink.toffee.models.request.RequestPayload;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.banglalink.toffee.models.schema.ConstantUtil.*;

@Slf4j
@Service
public class LoggingService {
    public static <T> void logResponse(T response, int status) {
        String message = String.format(RES_LOG_PATTERN,
                convertEntityToString(response),
                status
        );
        write_log(message);
    }

    public static String buildMessage(HttpServletRequest request, String payload) {
        return String
                .format(REQ_LOG_PATTERN,
                        request.getMethod(),
                        request.getRequestURI(),
                        extractHeaders(request),
                        payload,
                        request.getQueryString());
    }

    private static String extractHeaders(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        for (String header : REQ_LOG_HEADER_PATTERN) {
            String value = request.getHeader(header);
            if (value != null && !value.isEmpty()) {
                map.put(header, value);
            }
        }

        return new Gson().toJson(map);
    }

    public static String extractRequestPayload(JoinPoint joinPoint) {
        Optional<Object> payload = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> isSuperClassofRequestPayload(arg.getClass()))
                .findFirst();

        return payload.map(Object::toString).orElse("");
    }

    private static boolean isSuperClassofRequestPayload(Class<?> klass) {
        return klass.getSuperclass() == RequestPayload.class;
    }


    private static <T> String convertEntityToString(T entity) { return new Gson().toJson(entity); }

    public static void write_log(String msg) {
        log.info(msg);
    }
}
