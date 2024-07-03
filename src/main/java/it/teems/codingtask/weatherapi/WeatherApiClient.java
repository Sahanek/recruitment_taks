package it.teems.codingtask.weatherapi;

import it.teems.codingtask.dto.WeatherApiResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/** Basic weather api client, feel free to change this or rewrite from scratch. */
@Component
public class WeatherApiClient implements WeatherApi {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    public WeatherApiClient(
            RestTemplate restTemplate,
            @Value("${weather.api.base-url}") String baseUrl,
            @Value("${weather.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    @Override
    public WeatherApiResponse getWeather(String location) {
        String url =
                UriComponentsBuilder.fromHttpUrl(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("q", location)
                        .toUriString();
        return restTemplate.getForObject(url, WeatherApiResponse.class);
    }
}
