package it.teems.codingtask.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

import it.teems.codingtask.dto.WeatherApiResponse;
import it.teems.codingtask.dto.WeatherDetails;
import it.teems.codingtask.weatherapi.WeatherApi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class WeatherCollectorTest {
    @Mock WeatherApi weatherApi;

    @Mock WeatherDetailsService weatherDetailsService;

    @InjectMocks WeatherCollector weatherCollector;
    @Captor ArgumentCaptor<List<WeatherDetails>> captor;

    @BeforeEach
    void init() {
        weatherCollector =
                new WeatherCollector(weatherApi, List.of("London"), weatherDetailsService);
    }

    @Test
    void testCollector() {
        // given
        Mockito.when(weatherApi.getWeather("London"))
                .thenReturn(
                        new WeatherApiResponse(
                                new WeatherApiResponse.Location("London", "United Kingdom"),
                                new WeatherApiResponse.Current(1719252000, 26.1, 9, 0)));

        // when
        weatherCollector.collect();
        // then
        Mockito.verify(weatherDetailsService, times(1)).saveAll(captor.capture());
        List<WeatherDetails> value = captor.getValue();
        assertEquals(1, value.size());
        WeatherDetails result = value.getFirst();
        assertEquals("London", result.locationName());
    }
}
