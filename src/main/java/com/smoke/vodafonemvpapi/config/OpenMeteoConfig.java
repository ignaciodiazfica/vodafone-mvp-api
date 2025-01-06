package com.smoke.vodafonemvpapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "openmeteo")
public class OpenMeteoConfig {
    private String url;
    private int cacheTtlMinutes;

    public OpenMeteoConfig() {
    }
}
