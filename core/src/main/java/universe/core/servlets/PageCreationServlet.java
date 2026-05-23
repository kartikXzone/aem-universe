package universe.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.methods=POST",
                "sling.servlet.paths=/bin/universe/pageCreation"
        })

public class PageCreationServlet extends SlingAllMethodsServlet {

    public static final Logger log = LoggerFactory.getLogger(PageCreationServlet.class);

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws
            IOException, ServletException {

        response.setContentType("application/json");

        String parentPagePath = request.getParameter("parentPagePath");
        String pageTitle = request.getParameter("pageTitle");
        String templatePath = request.getParameter("templatePath");

        // check null values
        if (parentPagePath == null || pageTitle == null || templatePath == null) {
            response.setStatus(400);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Missing required parameters.\"}");
            return;
        }

        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

            String pageName = pageTitle.toLowerCase()
                    .replaceAll("[^a-z0-9\\-]", "-")
                    .replaceAll("-+", "-");

            if (pageManager == null) {
                response.setStatus(400);
                response.getWriter().write("{\"status\":\"error\",\"message\":\"PageManager not found.\"}");
                return;
            }

            Page page = pageManager.create(parentPagePath, pageName, templatePath, pageTitle);

            if (page != null) {
                resourceResolver.commit(); // save changes
                response.setStatus(200);
                response.getWriter().write("page created successfully");
            } else {
                response.setStatus(500);
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to create page.\"}");
            }
        } catch (Exception e) {
            log.error("Error creating page", e);
        }
    }
}
