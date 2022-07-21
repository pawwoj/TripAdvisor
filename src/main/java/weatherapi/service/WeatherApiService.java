package weatherapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import weatherapi.exceptions.JsonNodeFromUrlException;
import weatherapi.jsoncontroller.ObjectMapperHolder;
import weatherapi.model.CityWeatherForecast;
import weatherapi.model.Condition;
import weatherapi.model.WeatherForecast;
import model.WeatherInfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class WeatherApiService {

    private ObjectMapper objectMapper = ObjectMapperHolder.getMapper();

    public WeatherInfo getWeatherInfoFromCityWeatherForecast(CityWeatherForecast cityWeatherForecast, LocalDate lDate) {
        String city = cityWeatherForecast.getName();
        Condition condition = getConditionForDate(cityWeatherForecast.getWeatherForecast(), lDate);
        double temperature = getTemperatureForDate(cityWeatherForecast.getWeatherForecast(), lDate);

        return new WeatherInfo(lDate, city, temperature, condition);
    }

    public Condition getConditionForDate(List<WeatherForecast> weatherForecasts, LocalDate localDate) {
        return weatherForecasts
                .stream()
                .filter(w -> w.getLocalDate().isEqual(localDate))
                .findFirst().orElseThrow()
                .getCondition();
    }

    public double getTemperatureForDate(List<WeatherForecast> weatherForecasts, LocalDate localDate) {
        return weatherForecasts
                .stream()
                .filter(w -> w.getLocalDate().isEqual(localDate))
                .findFirst().orElseThrow()
                .getTemperature();
    }

    public String getLinkForCity(String city) {
        String key = "key=3d4611eff7504ad5a20182956223006";
        return "http://api.weatherapi.com/v1/forecast.json?" + key + "&q=" + city + "&days=7&aqi=no&alerts=no";
    }

    public CityWeatherForecast buildCityWeatherForecastFromJsonNode(JsonNode jsonNode) {
        CityWeatherForecast cityWeatherForecast = null;
        try {
            cityWeatherForecast = objectMapper.treeToValue(jsonNode, CityWeatherForecast.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return cityWeatherForecast;
    }

    public URL getUrlFromString(String string) {
        URL url;
        try {
            url = new URL(string);
        } catch (MalformedURLException e) {
            try {
                url = Path.of(string).toUri().toURL();
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return url;
    }

    public JsonNode getSourceJsonFromURL(URL url) {
        JsonNode json = null;
        try {
            json = objectMapper.readTree(url);
        } catch (IOException e) {
            throw new JsonNodeFromUrlException(e.getMessage());
        }
        return json;
    }

    public static Condition parseCondition(String text) {
        String s = text.toUpperCase().replace(" ", "_");
        return Condition.valueOf(s);
    }
}
