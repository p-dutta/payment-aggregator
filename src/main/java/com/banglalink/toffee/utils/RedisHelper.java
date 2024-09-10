package com.banglalink.toffee.utils;

import com.banglalink.toffee.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHelper {
    private final RedisService redisService;

    public Object checkKeyIntoAuthRedis(String key) {
        return redisService.getByKey(key, "auth");
    }

    public Object checkKeyIntoPaymentRedis(String key) {
        return redisService.getByKey(key, "payment");
    }

    public void saveValueIntoPaymentRedis(String key, Object value, long ttlInSeconds) {
        redisService.saveKeyValueToRedis(key, value, ttlInSeconds, "payment");
    }

    public void saveValueIntoAuthRedis(String key, Object value, long ttlInSeconds) {
        redisService.saveKeyValueToRedis(key, value, ttlInSeconds, "auth");
    }

    public <T> T getValueFromAuthRedis(String key, Class<T> valueType) {
        return redisService.getValueFromRedis("auth", key, valueType);
    }

    public <T> T getValueFromPaymentRedis(String key, Class<T> valueType) {
        return redisService.getValueFromRedis("payment", key, valueType);
    }

    public void removeKeyFromPaymentRedis(String key) {
        redisService.removeKeyFromRedis(key, "payment");
    }

    public void removeKeyFromAuthRedis(String key) {
        redisService.removeKeyFromRedis(key, "payment");
    }
}
