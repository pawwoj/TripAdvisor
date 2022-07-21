package weatherapi.service;

import model.WeatherInfo;
import org.junit.Before;
import org.junit.Test;
import weatherapi.exceptions.JsonNodeFromUrlException;
import weatherapi.model.CityWeatherForecast;
import weatherapi.model.Condition;
import weatherapi.model.WeatherForecast;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class WeatherApiServiceTest {
    private WeatherApiService wApiService;

    @Before
    public void init() {
        wApiService = new WeatherApiService();
    }

    @Test(expected = JsonNodeFromUrlException.class)
    public void shouldThrowJsonNodeFromUrlExceptionWhenUrlIsInvalid() {
        URL invalid;
        try {
            invalid = new URL("https://test.pl/test");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        wApiService.getSourceJsonFromURL(invalid);
    }

    @Test
    public void shouldReturnConditionAsExpected() {
        String s = "Partly cloudy";
        Condition expected = Condition.PARTLY_CLOUDY;

        assertEquals(WeatherApiService.parseCondition(s), expected);
    }

    @Test
    public void shouldReturnExpectedTemperature() {

        WeatherForecast wF1 = new WeatherForecast(LocalDate.of(2022, 7, 21),
                Condition.SUNNY, 26.5);
        WeatherForecast wF2 = new WeatherForecast(LocalDate.of(2022, 7, 22),
                Condition.SUNNY, 27.5);
        List<WeatherForecast> wfList = List.of(wF1, wF2);
        double expected = 27.5;

        double received = wApiService.getTemperatureForDate(wfList, LocalDate.of(2022, 7, 22));
        assertEquals(received, expected, 0.0);
    }

    @Test
    public void shouldReturnExpectedCondition() {

        WeatherForecast wF1 = new WeatherForecast(LocalDate.of(2022, 7, 21),
                Condition.BLIZZARD, 26.5);
        WeatherForecast wF2 = new WeatherForecast(LocalDate.of(2022, 7, 22),
                Condition.SUNNY, 27.5);
        List<WeatherForecast> wfList = List.of(wF1, wF2);
        Condition expected = Condition.SUNNY;

        Condition received = wApiService.getConditionForDate(wfList, LocalDate.of(2022, 7, 22));
        assertEquals(received, expected);
    }

    @Test
    public void shouldReturnExpectedWeatherInfo() {

        WeatherForecast wF1 = new WeatherForecast(LocalDate.of(2022, 7, 21),
                Condition.BLIZZARD, 26.5);
        WeatherForecast wF2 = new WeatherForecast(LocalDate.of(2022, 7, 22),
                Condition.SUNNY, 27.5);
        List<WeatherForecast> wfList = List.of(wF1, wF2);

        CityWeatherForecast cWForecast = new CityWeatherForecast("Warsaw", wfList);
        WeatherInfo expected = new WeatherInfo(LocalDate.of(2022, 7, 22),
                "Warsaw", 27.5, Condition.SUNNY);

        WeatherInfo received = wApiService.getWeatherInfoFromCityWeatherForecast(cWForecast,
                LocalDate.of(2022, 7, 22));
        assertEquals(received, expected);
    }
}