package universe.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import universe.core.beans.SearchBarPOJO;
import universe.core.services.SearchBarInterface;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchBarModel {

    @Getter
    @ValueMapValue
    private String search;

    @Inject
    ResourceResolver resourceResolver;

    @OSGiService
    SearchBarInterface searchBarInterface;

    private final String DEFAULT_PATH = "/content/universe-project/us/en";
    private String pageTitle;

    @Getter
    private List<SearchBarPOJO> resultList;

    @PostConstruct
    protected void init() {

        resultList = searchBarInterface.getSearchResult(DEFAULT_PATH, pageTitle ,search, resourceResolver);

    }
}
