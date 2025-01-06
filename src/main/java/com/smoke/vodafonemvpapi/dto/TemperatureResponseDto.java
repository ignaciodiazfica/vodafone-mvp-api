package com.smoke.vodafonemvpapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemperatureResponseDto {
    private Double longitude;
    private Double latitude;
    private Double temperature;

    public TemperatureResponseDto(Double longitude, Double latitude, Double temperature) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return String.format("Lat: %s, Lon: %s, Temp: %s", this.latitude, this.longitude, this.temperature);
    }
}
