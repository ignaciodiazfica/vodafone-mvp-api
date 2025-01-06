package com.smoke.vodafonemvpapi.repository;

import com.smoke.vodafonemvpapi.model.TemperatureCache;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemperatureCacheRepository extends MongoRepository<TemperatureCache, String> {

    Optional<TemperatureCache> findByLatitudeAndLongitude(Double latitude, Double longitude);

    void deleteByLatitudeAndLongitude(Double latitude, Double longitude);

}
