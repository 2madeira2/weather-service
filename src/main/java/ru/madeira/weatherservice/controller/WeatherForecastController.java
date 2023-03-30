package ru.madeira.weatherservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.madeira.weatherservice.integration.WeatherApiClient;
import ru.madeira.weatherservice.model.WeatherForecast;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@RestController
public class WeatherForecastController {

    private final WeatherApiClient weatherApiClient;

    @GetMapping("/forecast")
    public WeatherForecast getWeatherForecastByLocalityAndTimestamp(@RequestParam String locality, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime requestedDateTime, @Pattern(regexp = "^(cs|fh)$", message = "Temperature unit type must be celsius or fahrenheit") @RequestParam(defaultValue = "cs") String temperatureUnitType) throws JsonProcessingException {
        return weatherApiClient.getWeatherForecast(locality, requestedDateTime, temperatureUnitType);
    }

    @GetMapping("/current")
    public WeatherForecast getCurrentWeatherForecastByLocality(@RequestParam String locality, @Pattern(regexp = "^(cs|fh)$", message = "Temperature unit type must be celsius or fahrenheit") @RequestParam(defaultValue = "cs") String temperatureUnitType) throws JsonProcessingException {
        return weatherApiClient.getWeatherForecast(locality, null, temperatureUnitType);
    }

}
