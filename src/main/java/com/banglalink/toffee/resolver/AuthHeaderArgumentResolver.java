package com.banglalink.toffee.resolver;

import com.banglalink.toffee.annotation.AuthHeader;
import com.banglalink.toffee.exception.AuthException;
import com.banglalink.toffee.models.schema.JtiData;
import com.banglalink.toffee.utils.AuthUtil;
import com.banglalink.toffee.utils.HeaderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

import static com.banglalink.toffee.models.schema.ConstantUtil.HEADER_DECODING_PROFILE;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Component
@Slf4j
public class AuthHeaderArgumentResolver implements HandlerMethodArgumentResolver {
    private final PropertyResolver resolver;
    private final Environment environment;
    private final AuthUtil authUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthHeader.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        AuthHeader annotation = parameter.getParameterAnnotation(AuthHeader.class);
        boolean isRequired = Objects.requireNonNull(annotation).required();
        final String headerName = resolver.resolve(annotation.value());
        final String activeProfile = environment.getProperty("spring.profiles.active");
        String headerFromPropertyKey = webRequest.getHeader(headerName);

        if (StringUtils.isNotBlank(headerFromPropertyKey)) {
            if (Objects.requireNonNull(activeProfile).equalsIgnoreCase(HEADER_DECODING_PROFILE)) {
                log.info("Active Profile: {}", activeProfile);
                headerFromPropertyKey = HeaderUtil.getDecodedHeader(headerFromPropertyKey);
            }

            verifyJtiClaims(headerFromPropertyKey);

            return headerFromPropertyKey;
        } else if (isRequired)
            throw new MissingRequestHeaderException(headerName, parameter);

        return null;
    }

    private void verifyJtiClaims(String claims) throws JsonProcessingException {
        JtiData jtiData = authUtil.extractJtiClaims(claims);

        if (jtiData == null) {
            throw new AuthException("Unauthorized user");
        }
    }
}
