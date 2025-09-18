package universe.core.services;

import java.io.IOException;

public interface ExternalAPI {

    String getApiResult(String city) throws IOException;
}