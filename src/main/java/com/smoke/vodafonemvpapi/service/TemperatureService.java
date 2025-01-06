package com.smoke.vodafonemvpapi.service;

import com.smoke.vodafonemvpapi.api.OpenMeteoApi;
import com.smoke.vodafonemvpapi.config.OpenMeteoConfig;
import com.smoke.vodafonemvpapi.dto.TemperatureResponseDto;
import com.smoke.vodafonemvpapi.exception.TemperatureNotFoundException;
import com.smoke.vodafonemvpapi.model.TemperatureCache;
import com.smoke.vodafonemvpapi.repository.TemperatureCacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TemperatureService {

    private static final Logger log = LoggerFactory.getLogger(TemperatureService.class);

    private final TemperatureCacheRepository repository;
    private final OpenMeteoApi openMeteoApi;
    @Autowired
    private final OpenMeteoConfig openMeteoConfig;

    public TemperatureService(TemperatureCacheRepository repository, OpenMeteoApi openMeteoApi, OpenMeteoConfig openMeteoConfig) {
        this.repository = repository;
        this.openMeteoApi = openMeteoApi;
        this.openMeteoConfig = openMeteoConfig;
    }


    public TemperatureResponseDto getTemperature(Double latitude, Double longitude){
        log.info("Getting ({},{}) temperature from cache", latitude, longitude);
        Optional<TemperatureCache> cachedTemperatureOpt = repository.findByLatitudeAndLongitude(latitude, longitude);

        if(cachedTemperatureOpt.isPresent()){
            TemperatureCache cachedTemperature = cachedTemperatureOpt.get();
            Duration difference = Duration.between(cachedTemperature.getLastUpdated(), LocalDateTime.now());
            if(difference.toMinutes() < openMeteoConfig.getCacheTtlMinutes()){
                return new TemperatureResponseDto(longitude, latitude, cachedTemperature.getTemperature());
            }else{
                return fetchAndUpdateCache(latitude, longitude, cachedTemperature);
            }
        }else{
            Double temperature = openMeteoApi.getTemperatureFromApi(latitude, longitude);
            if(temperature == null){
                log.error("No temperature found from OpenMeteo API");
                throw new TemperatureNotFoundException("No temperature found from OpenMeteo API");
            }
            TemperatureCache newTemperatureCache = new TemperatureCache(latitude, longitude, temperature, LocalDateTime.now());
            repository.save(newTemperatureCache);
            log.info("Temperature ({},{}) saved in cache", latitude, longitude);
            return new TemperatureResponseDto(longitude, latitude, temperature);
        }
    }

    private TemperatureResponseDto fetchAndUpdateCache(Double latitude, Double longitude, TemperatureCache cachedTemperature){
        log.info("Fetching ({},{}) temperature from OpenMeteo API", latitude, longitude);
        Double updatedTemperature = openMeteoApi.getTemperatureFromApi(latitude, longitude);
        if(updatedTemperature != null){
            cachedTemperature.setTemperature(updatedTemperature);
            cachedTemperature.setLastUpdated(LocalDateTime.now());
            repository.save(cachedTemperature);
            return new TemperatureResponseDto(longitude, latitude, updatedTemperature);
        }
        // If the temperature is still null, throw an exception
        log.error("No temperature found from OpenMeteo API");
        throw new TemperatureNotFoundException("No temperature found from OpenMeteo API");
    }

    public void deleteCachedTemperature(Double latitude, Double longitude){
        log.info("Deleting ({},{}) temperature from cache", latitude, longitude);
        repository.deleteByLatitudeAndLongitude(latitude, longitude);
    }

}
