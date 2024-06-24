package it.teems.codingtask.weatherapi;

import com.fasterxml.jackson.databind.JsonNode;

public interface WeatherApi {

    // Feel free to modify
    JsonNode getWeather(String location);
}
