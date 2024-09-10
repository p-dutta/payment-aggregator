package com.banglalink.toffee.utils;

import com.banglalink.toffee.exception.BadRequestException;
import com.banglalink.toffee.models.dto.GatewaySubChannelDto;
import com.banglalink.toffee.models.dto.PaymentGatewayDto;
import com.banglalink.toffee.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.banglalink.toffee.models.schema.ConstantUtil.*;

@Component
@RequiredArgsConstructor
public class ValidationUtil {
    private final CacheService cacheService;

    public static void validateDateRanges(String startDate, String endDate) {
        if (!StringUtils.isBlank(startDate) && !StringUtils.isBlank(endDate)) {
            LocalDateTime from = LocalDateTime.parse(startDate, DATE_TIME_FORMATTER);
            LocalDateTime to = LocalDateTime.parse(endDate, DATE_TIME_FORMATTER);

            boolean startDatePresent = !to.isEqual(LocalDateTime.parse(START_OF_TIME, DATE_TIME_FORMATTER));
            boolean endDatePresent = !from.isEqual(LocalDateTime.parse(START_OF_TIME, DATE_TIME_FORMATTER));

            if (startDatePresent && endDatePresent && from.isAfter(to)) {
                throw new BadRequestException("Start date can not be ahead of end date");
            }
        }
    }

    public static String getEnumConstants(Class<? extends Enum<?>> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    public static void throwRequestArgumentValidationException(BindingResult bindingResult) {
        final String message = String.format("Validation constraint error: %s", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        throw new BadRequestException(message);
    }

    public PaymentGatewayDto fetchGatewayFromCache(UUID gatewayId, String subChannelId) {
        PaymentGatewayDto paymentGateway = cacheService.getFromCache(PGW_CACHE_STORE, gatewayId.toString(), PaymentGatewayDto.class);
        if (paymentGateway == null)
            throw new BadRequestException("Payment gateway for this operation is invalid!");

        List<GatewaySubChannelDto> subChannels = paymentGateway.getSubChannelDtos();
        GatewaySubChannelDto currentChannel = subChannels.stream().filter(c -> c.getSubChannelId().equals(subChannelId)).findFirst().orElse(null);
        if (subChannels.isEmpty() || ObjectUtils.isEmpty(currentChannel) || StringUtils.isEmpty(currentChannel.getBaseUrl()) || StringUtils.isEmpty(currentChannel.getExternalGatewayId()))
            throw new BadRequestException("Provided gateway channel for this operation is invalid/misconfigured!");

        return paymentGateway;
    }

    public String extractExternalGatewayId(PaymentGatewayDto paymentGateway, String subChannelId) {
        GatewaySubChannelDto subChannelDto = paymentGateway.getSubChannelDtos()
                .stream()
                .filter(ch -> ch.getSubChannelId().equals(subChannelId))
                .findFirst()
                .orElse(null);

        if (subChannelDto == null)
            throw new BadRequestException("Provided gateway channel for this operation is invalid/misconfigured!");
        return subChannelDto.getExternalGatewayId();
    }
}
