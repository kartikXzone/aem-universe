package universe.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Base64;

@Component(service = Servlet.class, property = {
        "sling.servlet.paths=/bin/universe/cookie",
        "sling.servlet.methods=GET"
})

public class CookieServlet extends SlingAllMethodsServlet {

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        // giving the cookie value
        String cookieValue = "firstCookieValue";
        String cookieName = "firstCookieName";

        // encode the cookie value:
        String encodedCookieValue = Base64.getEncoder().encodeToString(cookieValue.getBytes());

        // creating cookie and has been sent to the browser
        Cookie cookie = new Cookie(cookieName, encodedCookieValue);
        cookie.setMaxAge(3600); // in seconds
        cookie.setPath("/");
        response.addCookie(cookie);

        // retrieve the cookies from the browser
        String decodedCookieValue = "";
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray != null) {
            for (Cookie cookieArrayResult : cookieArray) {
                if (cookieArrayResult.getName().equals(cookieName)) {
                    // decode the cookie
                    byte[] decodeByteCookieValue = Base64.getDecoder().decode(cookieArrayResult.getValue());
                    decodedCookieValue = new String(decodeByteCookieValue);
                }
            }
        }

        // server response
        response.setStatus(200);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("cookie is created successfully....");
    }
}
