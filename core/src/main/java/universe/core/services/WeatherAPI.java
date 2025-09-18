package universe.core.services;

import java.io.IOException;

public interface WeatherAPI {
    public void getWeather(String city) throws IOException;
}
