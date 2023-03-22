package ru.filit.notificationapp.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    public static final int DEFAULT_TIMEOUT = 5000;

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }
}
