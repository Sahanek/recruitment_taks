package it.teems.codingtask.service;


import it.teems.codingtask.dto.WeatherApiResponse;
import it.teems.codingtask.dto.WeatherDetails;
import it.teems.codingtask.weatherapi.WeatherApi;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WeatherCollector {
    private final WeatherApi weatherApi;
    private final List<String> locations;
    private final WeatherDetailsService weatherDetailsService;

    public WeatherCollector(
            WeatherApi weatherApi,
            @Value("${weather.api.locations}") List<String> locations,
            WeatherDetailsService weatherDetailsService) {
        this.weatherApi = weatherApi;
        this.locations = locations;
        this.weatherDetailsService = weatherDetailsService;
    }

    @Scheduled(fixedDelayString = "${weather.api.collection.period.in.milliseconds}")
    public void collect() {
        List<WeatherApiResponse> weatherData = collectData();

        List<WeatherDetails> entities =
                weatherData.stream().map(WeatherDetailsMapper::mapToDetails).toList();

        weatherDetailsService.saveAll(entities);
    }

    private List<WeatherApiResponse> collectData() {
        log.info("Starting to retrieving data for locations {}", locations);
        List<WeatherApiResponse> weatherData =
                locations.stream().map(weatherApi::getWeather).toList();
        log.info("Data collected successfully.");
        return weatherData;
    }
}
