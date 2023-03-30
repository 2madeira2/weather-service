package ru.madeira.weatherservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WeatherForecast {

    private String locality;
    private TemperatureUnits temperatureUnits;
    private Integer temperature;

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "locality='" + locality + '\'' +
                ", temperatureUnits=" + temperatureUnits +
                ", temperature=" + temperature +
                '}';
    }
}
