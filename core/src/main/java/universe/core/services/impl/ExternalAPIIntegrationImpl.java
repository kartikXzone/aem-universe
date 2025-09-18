package universe.core.services.impl;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import universe.core.services.ExternalAPI;

import java.io.IOException;

@Component(service = ExternalAPI.class, immediate = true)
public class ExternalAPIIntegrationImpl implements ExternalAPI {

    private static final Logger log = LoggerFactory.getLogger(ExternalAPIIntegrationImpl.class);

    final String API_URL = "http://api.weatherapi.com/v1/current";
    final String API_KEY = "7f36b964032f40b8a8563025251507";

    @Override
    public String getApiResult(String city) throws IOException {

//        http://api.weatherapi.com/v1/current.json?key=<YOUR_API_KEY>&q=London

        String stringEntity;
        String finalURL = API_URL + ".json?key=" + API_KEY + "&q=" + city;
        HttpGet httpGet = new HttpGet(finalURL);

//        -> HTTP clients are required to make HTTP requests (like GET/POST).
//        -> HttpClients.createDefault() gives a default implementation of the HTTP client.
//        -> response.getEntity() gets the HTTP response body (typically JSON from API).

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            stringEntity = EntityUtils.toString(httpEntity); // Return raw JSON
            return stringEntity;
        } catch (IOException e) {
            log.error("catch block");
            return "{\"error\": \"Unable to fetch weather data\"}";
        }
    }
}
