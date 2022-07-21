package service;

import exceptions.DataOutOfRangeException;
import exceptions.InvalidCitiesListException;
import exceptions.InvalidCityNameException;
import exceptions.InvalidDataRangeException;
import interfaces.WeatherProvider;
import weatherapi.model.Condition;
import model.WeatherInfo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;


public class Service {

    private WeatherProvider weatherProvider;

    public Service(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    public String whereWillBeTheBestWeather(LocalDate from, LocalDate to, List<String> cities) {
        checkCities(cities);
        checkDaysRange(to);
        checkDaysBetween(from, to);
        List<WeatherInfo> weatherInfos = weatherProvider.getWeatherInfos(from, to, cities);
        System.out.println(weatherInfos);
        Map<String, List<WeatherInfo>> map = getMapCityWeatherInfo(weatherInfos);

        String city = map
                .entrySet()
                .stream()
                .max(Comparator.comparingDouble(l -> sumScore(l.getValue()))).orElseThrow()
                .getKey();

        return city;
    }

    public Map<String, List<WeatherInfo>> getMapCityWeatherInfo(List<WeatherInfo> weatherInfos) {
        return weatherInfos
                .stream().collect(Collectors.groupingBy(WeatherInfo::getCity));
    }

    public double sumScore(List<WeatherInfo> list) {
        return list
                .stream()
                .mapToDouble(this::singleWeatherScore)
                .sum();
    }

    public double singleWeatherScore(WeatherInfo weatherInfo) {
        return weatherInfo.getTemperature() * weatherInfo.getCondition().getMultiplier();
    }


    public void checkCities(List<String> cities) {
        if (cities == null) {
            throw new InvalidCitiesListException();
        }
        for (String city : cities) {
            if (city == null) {
                throw new InvalidCityNameException();
            }
        }
    }

    public void checkDaysRange(LocalDate to) {
        if ((int) DAYS.between(LocalDate.now(), to) > 6) {
            throw new DataOutOfRangeException("Max data range: " + LocalDate.now().plusDays(6));
        }
    }

    public void checkDaysBetween(LocalDate from, LocalDate to) {
        if (to.isBefore(from)) {
            throw new InvalidDataRangeException();
        }
    }

    public static Condition parseCondition(String text) {
        String s = text.toUpperCase().replace(" ", "_");
        return Condition.valueOf(s);
    }
}
