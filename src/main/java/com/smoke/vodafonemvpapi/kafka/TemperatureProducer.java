package com.smoke.vodafonemvpapi.kafka;

import com.smoke.vodafonemvpapi.dto.TemperatureResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TemperatureProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public TemperatureProducer(KafkaTemplate<String, String> kafkaTemplate, @Value("${kafka.topic.name:my-Topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendTemperatureMessage(TemperatureResponseDto dto){
        String message = dto.toString();
        kafkaTemplate.send(topic, message);
    }
}
