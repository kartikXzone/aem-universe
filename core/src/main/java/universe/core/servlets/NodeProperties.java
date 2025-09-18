package universe.core.servlets;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.osgi.service.component.annotations.Component;

import javax.inject.Inject;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

//Let’s say we want to get nodes and properties of certain node type and under a specified path.

@Component(service = Servlet.class,
        property = {
                ServletResolverConstants.SLING_SERVLET_PATHS + "=" + "/bin/universe/training/nodeProperties",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET
        })

public class NodeProperties extends SlingSafeMethodsServlet {

    public void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        String resourcePath = request.getParameter("path");

        // if you want the path to be static
//        String path = "/content/universe-project/us/en/centre-page/solar-system/moon/jcr:content/root/container/container/card";

        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource resource = resourceResolver.getResource(resourcePath);
        JsonObject jsonObject = new JsonObject();

        // check if resource is null or not
        if (resource != null) {
            ValueMap valueMap = resource.getValueMap();

            if (!valueMap.isEmpty()) {
                String address = valueMap.get("address", StringUtils.EMPTY);
                String gender = valueMap.get("gender", StringUtils.EMPTY);
                String name = valueMap.get("name", StringUtils.EMPTY);

                jsonObject.addProperty("address", address);
                jsonObject.addProperty("gender", gender);
                jsonObject.addProperty("name", name);

            } else {
                jsonObject.addProperty("error! ", " no properties found");
            }
        }
        response.getWriter().println(jsonObject);
        response.flushBuffer();
    }
}
