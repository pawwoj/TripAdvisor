package interfaces;

import model.WeatherInfo;

import java.time.LocalDate;
import java.util.List;

public interface WeatherProvider {
    List<WeatherInfo> getWeatherInfos(LocalDate from, LocalDate to, List<String> cities);
}
