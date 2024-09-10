package com.banglalink.toffee.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static com.banglalink.toffee.utils.LoggingService.*;
import static org.springframework.http.HttpMethod.*;

@Aspect
@Component
public class HttpRequestLoggingAspect {
    @Pointcut(
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)"
    )
    public void requestAction() {}


    @Before("requestAction()")
    public void logAction(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String payload = null;

        if (List.of(POST.name(), PUT.name()).contains(request.getMethod())) payload = extractRequestPayload(joinPoint);

        write_log(buildMessage(request, payload));
    }
}
