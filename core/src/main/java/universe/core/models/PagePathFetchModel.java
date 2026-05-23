package universe.core.models;

import com.day.cq.wcm.api.Page;
import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PagePathFetchModel {

    public static final Logger log = LoggerFactory.getLogger(PagePathFetchModel.class);

    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue
    private String name;

    @Getter
    private List<String> stringList;

    private final String keyword = "product";

    @PostConstruct
    protected void init() {

        stringList = new ArrayList<>();

        try {
            log.debug("product fetch model is started");
            Resource resource = resourceResolver.getResource(name);
            if (resource != null) {
                Page page = resource.adaptTo(Page.class);
                if (page != null) {
                    page.listChildren().forEachRemaining(childItem -> {
                        String childItemTitle = childItem.getTitle();
                        log.debug("page title: {}", childItemTitle);
                        if (childItemTitle != null && childItemTitle.contains(keyword)) {
                            stringList.add(childItem.getPath());
                            log.debug("After adding values into list: {}", stringList);
                        }
                    });
                }
            }
        } catch (Exception e) {
            log.debug("Exception handled");
        }
    }

    // 1. resource get 2. resource to page 3. page ke child list 4. add to list

}
