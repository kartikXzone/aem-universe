package universe.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import universe.core.services.ExternalAPI;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
        "sling.servlet.methods=GET",
        "sling.servlet.paths=/bin/universe/weatherAPI"
})

public class ExternalAPIServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    ExternalAPI externalAPI;

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException, ServletException {

        String city = request.getParameter("city");
        if (city == null || city.isEmpty()) {
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"City parameter is required\"}");
            return;
        }

        // Call the external API service
        try {
            String apiResult = externalAPI.getApiResult(city);
            response.setContentType("application/json");
            response.getWriter().write(apiResult);
        } catch (IOException e) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to fetch weather data\"}");
        }
    }
}