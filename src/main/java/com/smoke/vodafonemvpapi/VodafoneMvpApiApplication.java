package com.smoke.vodafonemvpapi;

import com.smoke.vodafonemvpapi.config.OpenMeteoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(OpenMeteoConfig.class)
public class VodafoneMvpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VodafoneMvpApiApplication.class, args);
    }

}
