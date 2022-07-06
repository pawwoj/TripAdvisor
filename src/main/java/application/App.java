package application;

import com.weatherapi.api.WeatherAPIClient;
import com.weatherapi.api.models.Day;
import com.weatherapi.api.models.ForecastJsonResponse;
import com.weatherapi.api.models.Forecastday;
import exceptions.InvalidCityNameException;
import interfaces.WeatherProvider;
import model.WeatherInfo;
import service.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class App {

    public void run(LocalDate from, LocalDate to, List<String> cities) {

        WeatherProvider weatherProvider;

        weatherProvider = (fromI, toI, citiesI) -> {
            List<WeatherInfo> weatherInfos = new ArrayList<>();
            ForecastJsonResponse forecastJsonResponse;
            int daysRange = (int) DAYS.between(LocalDate.now(), to) + 1;
            int daysBetween = (int) DAYS.between(from, to) + 1;
            for (String city : cities) {
                try {
                    forecastJsonResponse = new WeatherAPIClient().getAPIs()
                            .getForecastWeather(city, daysRange, null, null, 0, null);
                } catch (Throwable e) {
                    throw new InvalidCityNameException(e.getMessage());
                }
                List<Forecastday> forecastdays = forecastJsonResponse.getForecast().getForecastday();
                Day day;
                for (int i = daysRange - daysBetween; i < daysRange; i++) {
                    day = forecastdays.get(i).getDay();
                    weatherInfos
                            .add(new WeatherInfo(
                                    LocalDate.parse(forecastdays.get(i).getDate()),
                                    city,
                                    day.getAvgtempC(),
                                    Service.parseCondition(day.getCondition().getText())));
                }
            }
            return weatherInfos;
        };
        Service s = new Service(weatherProvider);
        System.out.println(s.whereWillBeTheBestWeather(from, to, cities));
    }
}
