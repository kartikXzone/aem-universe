//    package universe.core.services.impl;
//    import org.osgi.service.component.annotations.Component;
//    import universe.core.api.client.WeatherAPIClient;
//    import universe.core.api.dto.WeatherResponseDTO;
//    import universe.core.api.utills.ApiUtil;
//    import universe.core.services.WeatherAPI;
//
//    import java.io.IOException;
//
//    @Component(service = WeatherAPI.class, immediate = true)
//    public class WeatherAPIImpl implements WeatherAPI {
//
//        private final String API_URL = "https://www.weatherapi.com/my/";
//        private final String API_KEY = "ea7f7e80c2024b7bab651213252104";
//        private ApiUtil apiUtil = new ApiUtil();
//        @Override
//        public void getWeather(String city) throws IOException {
//             String URL = API_URL + "?q=" + city + "&appid=" + API_KEY;
//            final WeatherAPIClient client = apiUtil.createClient(WeatherAPIClient.class,API_URL);
//        }
//    }
//
