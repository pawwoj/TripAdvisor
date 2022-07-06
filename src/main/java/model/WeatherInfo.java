package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class WeatherInfo {
    private LocalDate data;
    private String city;
    private double temperature;
    private Condition condition;
}
