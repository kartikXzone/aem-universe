package universe.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import universe.core.services.OpenAIConnector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Serial;

@Component(service = Servlet.class,
        property = {"sling.servlet.methods=GET",
                "sling.servlet.paths=/bin/universe/openAI",
                "sling.servlet.extensions=json"})

public class OpenAIConnectorServlet extends SlingAllMethodsServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(OpenAIConnectorServlet.class);

    @Reference
    private OpenAIConnector openAIConnector;

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("application/json");


        String prompt = request.getParameter("prompt");

        if (prompt == null || prompt.trim().isEmpty()) {
            response.setStatus(400); // Bad Request
            response.getWriter().write("{\"error\":\"Prompt is missing.\"}");
            return;
        }

        try {
            String aiResponse = openAIConnector.getOpenAiResult(prompt);
            response.setStatus(200);
            response.getWriter().write("{\"Ai response \":\"" + aiResponse + "\"}");
            response.getWriter().write("Response status - 200");

        } catch (Exception e) {
            response.setStatus(500); // Internal Server Error
            response.getWriter().write("{\"response\":\"Something went wrong on the server.\"}");
        }
    }
}
