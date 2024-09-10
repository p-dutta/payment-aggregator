package com.banglalink.toffee.utils;

import com.banglalink.toffee.models.schema.JtiData;
import com.banglalink.toffee.models.schema.PayloadData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUtil {
    private final ObjectMapper objectMapper;
    private final RedisHelper redisHelper;

    public JtiData extractJtiClaims(String claims) throws JsonProcessingException {
        PayloadData jtiPayload = objectMapper.readValue(claims, PayloadData.class);

        return redisHelper.getValueFromAuthRedis(jtiPayload.getJti(), JtiData.class);
    }
}
