package weatherapi.jsoncontroller;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import weatherapi.model.CityWeatherForecast;
import model.WeatherInfo;

public class ObjectMapperHolder {

    private static ObjectMapper mapper = null;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CityWeatherForecastDeserializer wiRes = new CityWeatherForecastDeserializer(WeatherInfo.class);
        SimpleModule sm = new SimpleModule("CityWeatherForecastDeserializer",
                new Version(1, 0, 0, null, null, null));
        sm.addDeserializer(CityWeatherForecast.class, wiRes);
        mapper.registerModule(sm);

        mapper.findAndRegisterModules();
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
