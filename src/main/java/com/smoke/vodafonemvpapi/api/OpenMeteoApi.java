package com.smoke.vodafonemvpapi.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smoke.vodafonemvpapi.config.OpenMeteoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class OpenMeteoApi {

    Logger logger = LoggerFactory.getLogger(OpenMeteoApi.class);

    @Autowired
    private final OpenMeteoConfig openMeteoConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OpenMeteoApi(){
        this.openMeteoConfig = new OpenMeteoConfig();
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public Double getTemperatureFromApi(Double latitude, Double longitude){
        String url = String.format("%s?latitude=%s&longitude=%s&current_weather=true&temperature_unit=celsius", openMeteoConfig.getUrl(), latitude, longitude);
        logger.info("Getting temperature from OpenMeteo API");
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode currentWeather = root.get("current_weather");
            return currentWeather.get("temperature").asDouble();
        }catch (Exception e){
            logger.error("Error getting temperature from OpenMeteo API");
            return null;
        }
    }
}
