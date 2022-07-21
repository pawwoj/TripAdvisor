package weatherapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WeatherForecast {
    private LocalDate localDate;
    private Condition condition;
    private double temperature;
}
