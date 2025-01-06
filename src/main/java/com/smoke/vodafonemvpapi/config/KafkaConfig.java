package com.smoke.vodafonemvpapi.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic myTopic() {
        return new NewTopic("my-Topic", 1, (short) 1);
   }
}
