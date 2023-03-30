package ru.madeira.weatherservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.madeira.weatherservice.model.TemperatureUnits;
import ru.madeira.weatherservice.model.WeatherForecast;

import java.time.LocalDateTime;

@Component
public class WeatherForecastParser {

    public WeatherForecast parseJsonObject(String jsonResponseFromWeatherService, LocalDateTime weatherLocalDateTime, String temperatureUnitType) throws JsonProcessingException {
        JsonNode weatherForecastJsonNode = new ObjectMapper().readTree(jsonResponseFromWeatherService);
        String tempType = "cs".equals(temperatureUnitType) ? "temp_c" : "temp_f";
        int temperature = (weatherLocalDateTime != null) ? weatherForecastJsonNode
                .get("forecast")
                .get("forecastday")
                .get(0)
                .get("hour")
                .get(weatherLocalDateTime.getHour())
                .get(tempType)
                .asInt()
                :
                weatherForecastJsonNode
                .get("current")
                .get(tempType)
                .asInt();
        String locality = weatherForecastJsonNode
                .get("location")
                .get("name")
                .asText();
        return new WeatherForecast(locality, "cs".equals(temperatureUnitType) ? TemperatureUnits.CELSIUS : TemperatureUnits.FAHRENHEIT, temperature);
    }

}
