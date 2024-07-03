package it.teems.codingtask.repository;

import it.teems.codingtask.model.WeatherDetailsEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@Repository
public interface WeatherDetailsRepository
        extends PagingAndSortingRepository<WeatherDetailsEntity, BigInteger> {

    void saveAll(Iterable<WeatherDetailsEntity> entities);

    Page<WeatherDetailsEntity> findByLastUpdateBetweenAndLocationName(
            Instant from, Instant to, String locationName, Pageable pageable);

    Page<WeatherDetailsEntity> findByLastUpdateBetween(Instant from, Instant to, Pageable pageable);

    @Query(name = "WeatherDetailsEntity.findAvgTemperatureByLocationsBetweenDates")
    List<Object[]> findAvgTemperatureByLocationsBetweenDates(Instant from, Instant to);
}
