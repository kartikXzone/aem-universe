package universe.core.models;

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

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AssetCountModel {

    private static final Logger log = LoggerFactory.getLogger(AssetCountModel.class);

    @SlingObject
    ResourceResolver resourceResolver;

    @ValueMapValue
    private String damPath;

    @Getter
    private int count;

    @PostConstruct
    protected void init() {

        try {
            Resource resource = resourceResolver.getResource(damPath);
            if (resource == null) {
                log.warn("resource is null - no value fetched");
            }

            // count the assets or page under the given path in component
            if (resource != null) {
                for (Resource childResource : resource.getChildren()) {
                    if (childResource.isResourceType("dam:Asset") || childResource.isResourceType("cq:Page")) {
                        count++;
                    }
                }
            }
        } catch (Exception exception) {
            log.debug("exception is handled!");
        }
    }
}
