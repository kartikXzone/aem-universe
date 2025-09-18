package universe.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import universe.core.services.impl.SportActivitiesImpl;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

@Component(service = {Servlet.class},
        property = {
                "sling.servlet.paths=/bin/universe/query",
                "sling.servlet.methods=GET"})

public class QueryBuilderServlet extends SlingSafeMethodsServlet {

    @Reference
    private QueryBuilder queryBuilder;

    private List<String> list;
    private final String DEFAULT_PATH = "/content/universe-project/us/en/centre-page";

    private static final long serialVersionUID = 1L;

    public void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException, ServletException {

        list = new ArrayList<>();

        response.setContentType("application/json");

        // Must Call - adaption to session class is required for results.
        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);

        String dynamicKeyword = request.getParameter("keyword");
        String dynamicPath = request.getParameter("path");
        if (dynamicPath == null || dynamicPath.isEmpty()) {
            dynamicPath = DEFAULT_PATH;
        }

        // validating the dynamic path
        if (!dynamicPath.startsWith("/content")) {
            response.getWriter().write("Please enter a valid path which starts with /content");
            return;
        }

        // Create the predicates HashMap<String, String>
        Map<String, Object> predicates = new HashMap<>();
        predicates.put("path", dynamicPath);
        predicates.put("type", "cq:Page");
        predicates.put("fulltext", dynamicKeyword);

        // create a query via QueryBuilder API and store the result into SearchResult object
        Query searchQuery = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        SearchResult searchResult = searchQuery.getResult();

        // Iterate the resource and get the result
        searchResult.getResources().forEachRemaining(item -> {
            Page page = item.adaptTo(Page.class);
            if (page != null) {
                String pageTitle = page.getTitle().toUpperCase();
                String pagePath = page.getPath().concat(".html");
                list.add("Page title: " + pageTitle);
                list.add("Page path: " + pagePath);
            }
        });

        String result = new Gson().toJson(list);
        response.getWriter().println(result);
        response.flushBuffer();
    }
}
