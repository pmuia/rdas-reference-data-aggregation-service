package com.example.rdas.config;

import java.net.http.HttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SoapClientProperties.class)
public class SoapClientConfiguration {
    @Bean
    HttpClient countryInfoHttpClient(SoapClientProperties properties) {
        return HttpClient.newBuilder()
                .connectTimeout(properties.connectTimeout())
                .build();
    }
}
