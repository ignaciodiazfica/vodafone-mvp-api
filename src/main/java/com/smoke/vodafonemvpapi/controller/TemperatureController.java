package com.smoke.vodafonemvpapi.controller;

import com.smoke.vodafonemvpapi.dto.TemperatureResponseDto;
import com.smoke.vodafonemvpapi.kafka.TemperatureProducer;
import com.smoke.vodafonemvpapi.service.TemperatureService;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/temperature")
@Validated
public class TemperatureController {
    private final TemperatureService temperatureService;
    private final TemperatureProducer temperatureProducer;

    public TemperatureController(TemperatureService temperatureService, TemperatureProducer temperatureProducer) {
        this.temperatureService = temperatureService;
        this.temperatureProducer = temperatureProducer;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public TemperatureResponseDto getTemperature(@RequestParam("lat") @DecimalMin(value = "-90.0", message = "Latitude can't be less than -90") @DecimalMax(value = "90", message = "Latitude can't be grater than 90") Double latitude, @RequestParam("lon") @DecimalMin(value = "-180.0", message = "Longitude can't be less than -180") @DecimalMax(value = "180", message = "Longitude can't be grater than 180") Double longitude) {

        TemperatureResponseDto responseDto = temperatureService.getTemperature(latitude, longitude);
        temperatureProducer.sendTemperatureMessage(responseDto);
        return responseDto;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCachedTemperature(@RequestParam("lat") Double latitude, @RequestParam("lon") Double longitude) {
        temperatureService.deleteCachedTemperature(latitude, longitude);
    }
}
