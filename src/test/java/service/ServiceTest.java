package service;

import exceptions.DataOutOfRangeException;
import exceptions.InvalidCitiesListException;
import exceptions.InvalidCityNameException;
import exceptions.InvalidDataRangeException;
import interfaces.WeatherProvider;
import weatherapi.model.Condition;
import model.WeatherInfo;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

public class ServiceTest {
    private Service service;
    @Mock
    private WeatherProvider weatherProviderMock;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new Service(weatherProviderMock);

    }

    @Test
    public void shouldReturnExpectedCityToTravel() {
        LocalDate d1 = LocalDate.now().plusDays(5);
        LocalDate d2 = LocalDate.now().plusDays(6);
        List<String> list = new ArrayList<>(Arrays.asList("Warsaw", "London", "Barcelona"));
        WeatherInfo wI1 = new WeatherInfo(d1, "Warsaw", 25.0, Condition.CLOUDY);
        WeatherInfo wI2 = new WeatherInfo(d2, "Warsaw", 25.0, Condition.CLOUDY);
        WeatherInfo wI3 = new WeatherInfo(d1, "London", 20.0, Condition.HEAVY_RAIN);
        WeatherInfo wI4 = new WeatherInfo(d2, "London", 20.0, Condition.HEAVY_RAIN);
        WeatherInfo wI5 = new WeatherInfo(d1, "Barcelona", 30.0, Condition.SUNNY);
        WeatherInfo wI6 = new WeatherInfo(d2, "Barcelona", 30.0, Condition.SUNNY);
        List<WeatherInfo> weatherInfos = List.of(wI1, wI2, wI3, wI4, wI5, wI6);

        Mockito.when(weatherProviderMock.getWeatherInfos(d1, d2, list)).thenReturn(weatherInfos);

        SoftAssertions softA = new SoftAssertions();
        softA.assertThat(service.whereWillBeTheBestWeather(d1, d2, list)).isNotNull();
        softA.assertThat(service.whereWillBeTheBestWeather(d1, d2, list)).isInstanceOf(String.class);
        softA.assertThat(service.whereWillBeTheBestWeather(d1, d2, list)).isEqualTo("Barcelona");
        softA.assertAll();
    }

    @Test(expected = DataOutOfRangeException.class)
    public void shouldThrowDataOutOfRangeExceptionWhenDateToIsFurtherThen7Days() {
        LocalDate d1 = LocalDate.now().plusDays(2);
        LocalDate d2 = LocalDate.now().plusDays(7);
        List<String> list = new ArrayList<>(Arrays.asList("Warsaw", "London", "Barcelona"));

        service.whereWillBeTheBestWeather(d1, d2, list);
    }

    @Test(expected = InvalidDataRangeException.class)
    public void shouldThrowInvalidDataRangeExceptionWhenToIsBeforeFrom() {
        LocalDate d1 = LocalDate.now().plusDays(4);
        LocalDate d2 = LocalDate.now().plusDays(2);
        List<String> list = new ArrayList<>(Arrays.asList("Warsaw", "London", "Barcelona"));

        service.whereWillBeTheBestWeather(d1, d2, list);
    }

    @Test(expected = InvalidCitiesListException.class)
    public void shouldThrowInvalidCitiesListExceptionWhenCitiesListIsNull() {
        LocalDate d1 = LocalDate.now().plusDays(4);
        LocalDate d2 = LocalDate.now().plusDays(2);
        List<String> list = null;

        service.whereWillBeTheBestWeather(d1, d2, list);
    }

    @Test(expected = InvalidCityNameException.class)
    public void shouldThrowInvalidCityNameExceptionWhenCitiesListContainNull() {
        LocalDate d1 = LocalDate.now().plusDays(4);
        LocalDate d2 = LocalDate.now().plusDays(5);
        List<String> list = new ArrayList<>(Arrays.asList("Warsaw", "London", null));

        service.whereWillBeTheBestWeather(d1, d2, list);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowInvalidCityNameExceptionWhenCitiesListContainUnknown() {
        LocalDate d1 = LocalDate.now().plusDays(4);
        LocalDate d2 = LocalDate.now().plusDays(5);
        List<String> list = new ArrayList<>(Arrays.asList("Warsaw", "London", "Unknown1"));

        service.whereWillBeTheBestWeather(d1, d2, list);
    }

    @Test
    public void shouldReturnExpectedDouble() {
        WeatherInfo wI1 = new WeatherInfo(LocalDate.now(), "Warsaw", 10.0, Condition.LIGHT_SLEET_SHOWERS);

        SoftAssertions softA = new SoftAssertions();
        softA.assertThat(service.singleWeatherScore(wI1)).isNotNull();
        softA.assertThat(service.singleWeatherScore(wI1)).isInstanceOf(Double.class);
        softA.assertThat(service.singleWeatherScore(wI1)).isEqualTo(100.0);
        softA.assertAll();
    }

    @Test
    public void shouldReturnExpectedSum() {
        WeatherInfo wI1 = new WeatherInfo(LocalDate.now(), "Warsaw", 10.0, Condition.LIGHT_SLEET_SHOWERS);
        WeatherInfo wI2 = new WeatherInfo(LocalDate.now(), "Warsaw", 2.0, Condition.HEAVY_SNOW);

        SoftAssertions softA = new SoftAssertions();
        softA.assertThat(service.sumScore(List.of(wI1, wI2))).isNotNull();
        softA.assertThat(service.sumScore(List.of(wI1, wI2))).isInstanceOf(Double.class);
        softA.assertThat(service.sumScore(List.of(wI1, wI2))).isEqualTo(130.0);
        softA.assertAll();
    }

    @Test
    public void shouldReturnExpectedMap() {
        WeatherInfo wI1 = new WeatherInfo(LocalDate.now(), "Warsaw", 10.0, Condition.HEAVY_SNOW);
        WeatherInfo wI2 = new WeatherInfo(LocalDate.now(), "Warsaw", 10.0, Condition.SUNNY);
        WeatherInfo wI3 = new WeatherInfo(LocalDate.now(), "London", 10.0, Condition.HEAVY_SNOW);
        WeatherInfo wI4 = new WeatherInfo(LocalDate.now(), "London", 10.0, Condition.SUNNY);
        List<WeatherInfo> weatherInfos = List.of(wI1, wI2, wI3, wI4);
        SoftAssertions softA = new SoftAssertions();
        softA.assertThat(service.getMapCityWeatherInfo(weatherInfos)).isNotNull();
        softA.assertThat(service.getMapCityWeatherInfo(weatherInfos)).isInstanceOf(Map.class);
        softA.assertThat(service.getMapCityWeatherInfo(weatherInfos).get("Warsaw")).isEqualTo(List.of(wI1, wI2));
        softA.assertThat(service.getMapCityWeatherInfo(weatherInfos).get("London")).isEqualTo(List.of(wI3, wI4));
        softA.assertAll();
    }
}