package it.teems.codingtask.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import it.teems.codingtask.dto.LocationAvgTemperature;
import it.teems.codingtask.dto.PageProperties;
import it.teems.codingtask.dto.PagedResult;
import it.teems.codingtask.dto.WeatherDetails;
import it.teems.codingtask.model.WeatherDetailsEntity;
import it.teems.codingtask.repository.WeatherDetailsRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WeatherDetailsServiceTest {
    @Mock
    private WeatherDetailsRepository weatherDetailsRepository;

    @InjectMocks
    private WeatherDetailsService weatherService;

    @Test
    void testGetWeatherDetails() {
        //given
        LocalDate fromDate = LocalDate.now().minusDays(10);
        LocalDate toDate = LocalDate.now();
        String location = "TestLocation";
        PageProperties pageProperties = new PageProperties(0, 10);

        Instant from = fromDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant to = toDate.atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC);
        Pageable pageable = PageRequest.of(pageProperties.getPage(), pageProperties.getSize());

        Page<WeatherDetailsEntity> weatherDetailsEntities = new PageImpl<>(Collections.emptyList());

        when(weatherDetailsRepository.findByLastUpdateBetweenAndLocationName(from, to, location, pageable))
                .thenReturn(weatherDetailsEntities);

        //when
        PagedResult<WeatherDetails> result = weatherService.getWeatherDetails(fromDate, toDate, location, pageProperties);

        //then
        assertNotNull(result);
        assertEquals(0, result.numberOfElements());
    }

    @Test
    void testFindAvgTemperatureByLocationsInDate() {
        //given
        LocalDate atDate = LocalDate.now();
        Instant beginDate = atDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endDate = beginDate.plus(1, ChronoUnit.DAYS).minusNanos(1);

        Object[] mockResult = {"TestLocation", 25.0};
        List<Object[]> avgTemperatureByLocationsBetweenDates = Collections.singletonList(mockResult);
        LocationAvgTemperature locationAvgTemperature = new LocationAvgTemperature("TestLocation", 25.0);

        when(weatherDetailsRepository.findAvgTemperatureByLocationsBetweenDates(beginDate, endDate))
                .thenReturn(avgTemperatureByLocationsBetweenDates);

        //when
        List<LocationAvgTemperature> result = weatherService.findAvgTemperatureByLocationsInDate(atDate);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(locationAvgTemperature, result.getFirst());
    }

    @Test
    void testSaveAll() {
        //given
        List<WeatherDetails> weatherDetails = Collections.singletonList(new WeatherDetails("locationName", "2018-11-30T18:35:24.00Z", "UK", 20.1, 9.0, 10));

        //when
        weatherService.saveAll(weatherDetails);

        //then
        verify(weatherDetailsRepository, times(1)).saveAll(anyIterable());
    }
}
