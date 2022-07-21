package application;

import com.fasterxml.jackson.databind.JsonNode;
import interfaces.WeatherProvider;
import weatherapi.model.CityWeatherForecast;
import model.WeatherInfo;
import service.Service;
import weatherapi.service.WeatherApiService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class App {

    public void run(LocalDate from, LocalDate to, List<String> cities) {
        WeatherApiService wApiService = new WeatherApiService();
        WeatherProvider weatherProvider;

        weatherProvider = (fromI, toI, citiesI) -> {
            List<WeatherInfo> weatherInfos = new ArrayList<>();
            List<CityWeatherForecast> cityWeatherForecasts = new ArrayList<>();
            int daysBetween = (int) DAYS.between(from, to) + 1;
            for (String city : cities) {
                URL url = wApiService.getUrlFromString(wApiService.getLinkForCity(city));
                JsonNode jsonNode = wApiService.getSourceJsonFromURL(url);
                cityWeatherForecasts.add(wApiService.buildCityWeatherForecastFromJsonNode(jsonNode));
            }
            for (CityWeatherForecast cityWeatherForecast : cityWeatherForecasts) {
                for (int i = 0; i < daysBetween; i++) {
                    weatherInfos.add(wApiService
                            .getWeatherInfoFromCityWeatherForecast(cityWeatherForecast, from.plusDays(i)));
                }
            }
            return weatherInfos;
        };
        Service s = new Service(weatherProvider);
        System.out.println(s.whereWillBeTheBestWeather(from, to, cities));
    }
}
