package com.smoke.vodafonemvpapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Document(collection = "temperature_cache")
@CompoundIndex(name = "lat_lon_idx", def = "{'latitude': 1, 'longitude': 1}", unique = true)
public class TemperatureCache {

    @Id
    private String id;

    private Double latitude;
    private Double longitude;
    private Double temperature;
    private LocalDateTime lastUpdated;
        @Indexed(name = "ttl_idx", expireAfter = "60s")
    private Date createdAt;

    public TemperatureCache() {
    }

    public TemperatureCache(Double latitude, Double longitude, Double temperature, LocalDateTime lastUpdated) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.lastUpdated = lastUpdated;
        this.createdAt = new Date();
    }


}
