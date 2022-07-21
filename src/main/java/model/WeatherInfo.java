package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import weatherapi.model.Condition;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInfo {
    private LocalDate data;
    private String city;
    private double temperature;
    private Condition condition;
}
