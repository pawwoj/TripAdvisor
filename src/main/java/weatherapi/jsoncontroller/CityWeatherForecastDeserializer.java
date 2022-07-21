package weatherapi.jsoncontroller;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import weatherapi.model.CityWeatherForecast;
import weatherapi.model.Condition;
import weatherapi.model.WeatherForecast;
import service.Service;
import weatherapi.service.WeatherApiService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CityWeatherForecastDeserializer extends StdDeserializer<CityWeatherForecast> {

    public CityWeatherForecastDeserializer() {
        this(null);
    }

    @Override
    public CityWeatherForecast deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
        JsonNode productNode = p.getCodec().readTree(p);
        CityWeatherForecast cityWeatherForecast = new CityWeatherForecast();
        cityWeatherForecast.setName(productNode.get("location").get("name").asText());
        List<WeatherForecast> weatherForecasts = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            double avgTemp = productNode.get("forecast")
                    .get("forecastday").get(i).get("day").get("avgtemp_c").asDouble();
            Condition con = WeatherApiService.parseCondition(productNode.get("forecast")
                    .get("forecastday").get(i).get("day").get("condition").get("text").asText());
            LocalDate localDate = LocalDate.parse(productNode.get("forecast")
                    .get("forecastday").get(i).get("date").asText());
            weatherForecasts.add(new WeatherForecast(localDate, con, avgTemp));
        }
        cityWeatherForecast.setWeatherForecast(weatherForecasts);
        return cityWeatherForecast;
    }

    public CityWeatherForecastDeserializer(Class<?> vc) {
        super(vc);
    }
}
