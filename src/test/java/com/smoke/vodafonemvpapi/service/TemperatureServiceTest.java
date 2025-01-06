package com.smoke.vodafonemvpapi.service;

import com.smoke.vodafonemvpapi.api.OpenMeteoApi;
import com.smoke.vodafonemvpapi.config.OpenMeteoConfig;
import com.smoke.vodafonemvpapi.model.TemperatureCache;
import com.smoke.vodafonemvpapi.repository.TemperatureCacheRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

public class TemperatureServiceTest {

    @Test
    void testGetTemperatureFromCache(){
        TemperatureCacheRepository mockRepository = Mockito.mock(TemperatureCacheRepository.class);
        OpenMeteoApi mockOpenMeteoApi = Mockito.mock(OpenMeteoApi.class);
        OpenMeteoConfig mockOpenMeteoConfig = Mockito.mock(OpenMeteoConfig.class);

        when(mockOpenMeteoConfig.getCacheTtlMinutes()).thenReturn(1);

        TemperatureService service = new TemperatureService(mockRepository, mockOpenMeteoApi, mockOpenMeteoConfig);
        TemperatureCache cachedTemperature = new TemperatureCache(10.0, 20.0, 25.0, LocalDateTime.now());
        when(mockRepository.findByLatitudeAndLongitude(10.0, 20.0))
                .thenReturn(Optional.of(cachedTemperature));

        var response = service.getTemperature(10.0, 20.0);

        assertEquals(25.0, response.getTemperature());
        Mockito.verify(mockRepository, Mockito.never()).save(cachedTemperature);
        Mockito.verify(mockOpenMeteoApi, Mockito.never()).getTemperatureFromApi(anyDouble(), anyDouble());
    }
}
