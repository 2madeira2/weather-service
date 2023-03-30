package ru.madeira.weatherservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.madeira.weatherservice.exception.WeatherApiConnectException;
import ru.madeira.weatherservice.model.WeatherForecast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
@Component
public class WeatherApiClient {

    @Value("${weather.api.base-url}")
    private String apiBaseUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private final WeatherForecastParser weatherForecastParser;

    public WeatherForecast getWeatherForecast(String locality, LocalDateTime requestDateTime, String temperatureUnitType) throws JsonProcessingException {
        Map<String, String> queryParams = new HashMap<>();
        String endPath = "/current.json?key={key}&q={q}&lang={lang}";
        queryParams.put("key", apiKey);
        queryParams.put("q", locality);
        queryParams.put("lang", "ru");
        if (requestDateTime != null) {
            queryParams.put("dt", requestDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            endPath = "/history.json?key={key}&q={q}&dt={dt}&lang={lang}";
        }
        ResponseEntity<String> weatherApiResponse;
        try {
            weatherApiResponse = restTemplate.getForEntity(apiBaseUrl + endPath, String.class, queryParams);
        } catch (HttpClientErrorException exception) {
            throw new WeatherApiConnectException();
        }
        return weatherForecastParser.parseJsonObject(weatherApiResponse.getBody(), requestDateTime, temperatureUnitType);
    }

}
