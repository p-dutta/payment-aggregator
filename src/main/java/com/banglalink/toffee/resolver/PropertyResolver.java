package com.banglalink.toffee.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PropertyResolver {
    private final Environment environment;

    public String resolve(String propertyName) {
        return environment.getProperty(propertyName);
    }
}
