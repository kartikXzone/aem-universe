package universe.core.services.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import universe.core.beans.SearchBarPOJO;
import universe.core.services.SearchBarInterface;

import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = SearchBarInterface.class, immediate = true)
public class SearchBarImpl implements SearchBarInterface {

    @Reference
    private QueryBuilder queryBuilder;

    final String DEFAULT_PATH = "cq:Page";
    final String TEMPLATE_PATH = "/conf/universe-project/settings/wcm/templates/planets";

    @Override
    public List<SearchBarPOJO> getSearchResult(String pagePath, String pageTitle, String searchTerm, ResourceResolver resourceResolver) {

        List<SearchBarPOJO> list = new ArrayList<>();
        Session session = resourceResolver.adaptTo(Session.class);

        if (pagePath != null && !searchTerm.isEmpty()) {
            Map<String, String> predicates = new HashMap<>();

            // strict search - the search should function like:
            // if the search keyword is "mer" then show all the results where mer is present on the page, not mercury has to be entered fully.
            predicates.put("fulltext", searchTerm);
            predicates.put("type", DEFAULT_PATH);
            predicates.put("path", pagePath);
            predicates.put("1_property", "jcr:content/cq:template");
            predicates.put("1_property.value", TEMPLATE_PATH);

            Query query = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
            SearchResult result = query.getResult();

            result.getResources().forEachRemaining(resource -> {
                Page page = resource.adaptTo(Page.class);
                if (page != null) {
                    String resultedPagePath = page.getPath().concat(".html");
                    String resultedPageTitle = page.getTitle();
                    SearchBarPOJO searchBarPOJO = new SearchBarPOJO(resultedPagePath, resultedPageTitle);
                    list.add(searchBarPOJO);
                }
            });
        }
        return list;
    }
}
