package universe.core.servlets;

import com.adobe.fontengine.font.PDFEncodingBuilder;
import com.day.cq.search.QueryBuilder;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import universe.core.services.SportActivitiesInterface;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

// Path base servlet:

// @Component(service = {Servlet.class})
//@SlingServletPaths(value = {"/bin/universe/training/sports", "/bin/universe/sports"})
// -> can remove value keyword also, give direct paths

 // second way for calling path base servlet:
@Component(service = {Servlet.class},
        property = {
                ServletResolverConstants.SLING_SERVLET_PATHS + "=" + "/bin/universe/training",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET
        }
)

public class SportActivitiesServlet extends SlingSafeMethodsServlet {

    @Reference
    private SportActivitiesInterface sportActivitiesInterface;

    private static final long serialVersionUID = 1L;

    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("application/json");
        PrintWriter print = response.getWriter();
        print.println("sports servlet is working...");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sport name: ", sportActivitiesInterface.getSport());
        String s = jsonObject.toString();

        print.println("The servlet value is: " + s);
    }
}



// Resource base servlet
//@Component(service = {Servlet.class})
//
//@SlingServletResourceTypes(
//        resourceTypes = "universe-project/components/page",
//        methods = HttpConstants.METHOD_GET,
//        extensions = "txt"
//)
//
//public class SportActivitiesServlet extends SlingAllMethodsServlet {
//
//    private static final Logger logger = LoggerFactory.getLogger(SportActivitiesServlet.class);
//    private static final long serialVersionUID = 1L;
//
//    @Reference
//    private SportActivitiesInterface sportActivitiesInterface;
//
//    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
//            throws ServletException, IOException {
//
//        PrintWriter printWriter = response.getWriter();
//        printWriter.println("Sport Servlet - Post Method is working fine....");
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("sport name: ", sportActivitiesInterface.getSport());
//
//        printWriter.println("The servlet value is: " + jsonObject);
//
//    }
//}

