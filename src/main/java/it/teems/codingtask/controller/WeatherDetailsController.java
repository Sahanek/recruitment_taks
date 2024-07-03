package it.teems.codingtask.controller;

import it.teems.codingtask.dto.LocationAvgTemperature;
import it.teems.codingtask.dto.PageProperties;
import it.teems.codingtask.dto.PagedResult;
import it.teems.codingtask.dto.WeatherDetails;
import it.teems.codingtask.service.WeatherDetailsService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/weather-details")
public class WeatherDetailsController {

    WeatherDetailsService weatherDetailsService;

    @GetMapping
    public ResponseEntity<PagedResult<WeatherDetails>> getAllBy(
            @RequestParam(name = "from", required = false) LocalDate fromDate,
            @RequestParam(name = "to", required = false) LocalDate toDate,
            @RequestParam(name = "location", required = false) String location,
            PageProperties pageProperties) {
        return ResponseEntity.ok(
                weatherDetailsService.getWeatherDetails(
                        fromDate, toDate, location, pageProperties));
    }

    @GetMapping("/location-average")
    public ResponseEntity<List<LocationAvgTemperature>> getAvgTemperatureByLocationsAtDay(
            @RequestParam("atDate") LocalDate atDate) {
        return ResponseEntity.ok(weatherDetailsService.findAvgTemperatureByLocationsInDate(atDate));
    }
}
