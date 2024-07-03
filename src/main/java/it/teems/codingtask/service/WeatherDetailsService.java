package it.teems.codingtask.service;

import it.teems.codingtask.dto.LocationAvgTemperature;
import it.teems.codingtask.dto.PageProperties;
import it.teems.codingtask.dto.PagedResult;
import it.teems.codingtask.dto.WeatherDetails;
import it.teems.codingtask.model.WeatherDetailsEntity;
import it.teems.codingtask.repository.WeatherDetailsRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherDetailsService {

    private WeatherDetailsRepository weatherDetailsRepository;

    public PagedResult<WeatherDetails> getWeatherDetails(
            LocalDate fromDate, LocalDate toDate, String location, PageProperties pageProperties) {
        Instant from = calculateFromDate(fromDate);
        Instant to = calculateToDate(toDate);
        Pageable pageable = PageRequest.of(pageProperties.getPage(), pageProperties.getSize());
        Page<WeatherDetailsEntity> weatherDetailsEntities =
                getWeatherDetailsEntitiesPageable(from, to, location, pageable);
        return WeatherDetailsMapper.mapToPagedWeatherDetails(weatherDetailsEntities);
    }

    public List<LocationAvgTemperature> findAvgTemperatureByLocationsInDate(LocalDate atDate) {
        Instant beginDate = atDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endDate = beginDate.plus(1, ChronoUnit.DAYS).minusNanos(1);
        List<Object[]> avgTemperatureByLocationsBetweenDates =
                weatherDetailsRepository.findAvgTemperatureByLocationsBetweenDates(
                        beginDate, endDate);

        return avgTemperatureByLocationsBetweenDates.stream()
                .map(WeatherDetailsMapper::mapToLocationAvgTemperature)
                .toList();
    }

    @Transactional
    public void saveAll(List<WeatherDetails> weatherDetails) {
        weatherDetailsRepository.saveAll(
                weatherDetails.stream().map(WeatherDetailsMapper::mapToEntity).toList());
    }

    private Page<WeatherDetailsEntity> getWeatherDetailsEntitiesPageable(
            Instant from, Instant to, String location, Pageable pageable) {
        return location == null
                ? weatherDetailsRepository.findByLastUpdateBetween(from, to, pageable)
                : weatherDetailsRepository.findByLastUpdateBetweenAndLocationName(
                        from, to, location, pageable);
    }

    private Instant calculateToDate(LocalDate toDate) {
        return toDate == null ? Instant.now() : toDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    private Instant calculateFromDate(LocalDate fromDate) {
        return fromDate == null
                ? LocalDate.now().atStartOfDay().minusDays(7).toInstant(ZoneOffset.UTC)
                : fromDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
