package com.banglalink.toffee.config;

import com.banglalink.toffee.models.schema.ConstantUtil;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.List;

import static com.banglalink.toffee.models.schema.ConstantUtil.*;

@Configuration
public class WebClientConfig {
    @Value("${app.paywall.client-id}")
    private String PAYWALL_CLIENT_ID;

    @Value("${app.paywall.client-secret}")
    private String PAYWALL_CLIENT_SECRET;

    @Value("${app.paywall.base-url}")
    private String PAYWALL_GW_BASE_URL;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(buildHttpClient()));
    }

    @Bean("payWallWebClient")
    //@Qualifier("payWallWebClient")
    public WebClient payWallWebClient(WebClient.Builder builder) {
        return builder.baseUrl(PAYWALL_GW_BASE_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.set(PAYWALL_CLIENT_ID_KEY, PAYWALL_CLIENT_ID);
                    httpHeaders.set(PAYWALL_CLIENT_SECRET_KEY, PAYWALL_CLIENT_SECRET);
                })
                .build();
    }

    private HttpClient buildHttpClient() {
        return HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
    }
}
