package com.example.rdas.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rdas.soap")
public record SoapClientProperties(Duration connectTimeout, Duration readTimeout, String endpoint) {
}
