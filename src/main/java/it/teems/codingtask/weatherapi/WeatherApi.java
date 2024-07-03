package it.teems.codingtask.weatherapi;

import it.teems.codingtask.dto.WeatherApiResponse;

public interface WeatherApi {

    // Feel free to modify
    WeatherApiResponse getWeather(String location);
}
