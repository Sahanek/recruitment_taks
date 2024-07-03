package it.teems.codingtask.dto;

import lombok.Builder;

@Builder
public record WeatherDetails(
        String locationName,
        String lastUpdate,
        String country,
        Double temperatureCelsius,
        Double windKph,
        Integer precipMm) {}
