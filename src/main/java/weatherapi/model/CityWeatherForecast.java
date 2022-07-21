package weatherapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityWeatherForecast {
    private String name;
    private List<WeatherForecast> weatherForecast;
}
