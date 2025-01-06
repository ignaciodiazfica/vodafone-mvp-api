package com.smoke.vodafonemvpapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenMeteoController {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OpenMeteoController(){
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public Double getTemperatureFromApi(Double latitude, Double longitude){
        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true&temperature_unit=celsius", latitude, longitude);
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode currentWeather = root.get("current_weather");
            return currentWeather.get("temperature").asDouble();
        }catch (Exception e){
            e.printStackTrace(); // TODO: replace with logger
            return null;
        }
    }
}
