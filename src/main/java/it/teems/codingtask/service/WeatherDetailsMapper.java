package it.teems.codingtask.service;

import it.teems.codingtask.dto.LocationAvgTemperature;
import it.teems.codingtask.dto.PagedResult;
import it.teems.codingtask.dto.WeatherApiResponse;
import it.teems.codingtask.dto.WeatherDetails;
import it.teems.codingtask.model.WeatherDetailsEntity;

import org.springframework.data.domain.Page;

import java.time.Instant;

public class WeatherDetailsMapper {

    private WeatherDetailsMapper() {}

    public static WeatherDetailsEntity mapToEntity(WeatherDetails details) {
        return WeatherDetailsEntity.builder()
                .locationName(details.locationName())
                .country(details.country())
                .windKph(details.windKph())
                .lastUpdate(Instant.parse(details.lastUpdate()))
                .temperatureCelsius(details.temperatureCelsius())
                .precipMm(details.precipMm())
                .build();
    }

    public static WeatherDetails mapToDetails(WeatherApiResponse response) {
        return WeatherDetails.builder()
                .locationName(response.location().name())
                .country(response.location().country())
                .windKph(response.current().windKph())
                .lastUpdate(Instant.ofEpochSecond(response.current().lastUpdatedEpoch()).toString())
                .temperatureCelsius(response.current().tempC())
                .precipMm(response.current().precipMm())
                .build();
    }

    public static WeatherDetails mapToWeatherDetails(WeatherDetailsEntity entity) {
        return WeatherDetails.builder()
                .locationName(entity.getLocationName())
                .country(entity.getCountry())
                .windKph(entity.getWindKph())
                .lastUpdate(entity.getLastUpdate().toString())
                .temperatureCelsius(entity.getTemperatureCelsius())
                .precipMm(entity.getPrecipMm())
                .build();
    }

    public static PagedResult<WeatherDetails> mapToPagedWeatherDetails(
            Page<WeatherDetailsEntity> weatherDetailsEntityPage) {
        return new PagedResult<>(
                weatherDetailsEntityPage
                        .get()
                        .map(WeatherDetailsMapper::mapToWeatherDetails)
                        .toList(),
                weatherDetailsEntityPage.getNumber(),
                weatherDetailsEntityPage.getTotalPages(),
                weatherDetailsEntityPage.getSize(),
                weatherDetailsEntityPage.getNumberOfElements());
    }

    public static LocationAvgTemperature mapToLocationAvgTemperature(
            Object[] locationAvgTemperature) {
        return new LocationAvgTemperature(
                (String) locationAvgTemperature[0], (Double) locationAvgTemperature[1]);
    }
}
