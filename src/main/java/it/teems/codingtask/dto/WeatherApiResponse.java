package it.teems.codingtask.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record WeatherApiResponse(Location location, Current current) {
    public record Current(
            @JsonAlias("last_updated_epoch") long lastUpdatedEpoch,
            @JsonAlias("temp_c") double tempC,
            @JsonAlias("wind_kph") double windKph,
            @JsonAlias("precip_mm") int precipMm) {}

    public record Location(String name, String country) {}
}
