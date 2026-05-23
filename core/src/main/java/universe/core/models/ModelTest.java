package universe.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import java.util.Objects;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ModelTest {

    private static final Logger logger = LoggerFactory.getLogger(ModelTest.class);

    @SlingObject
    SlingHttpServletRequest request;

    @Getter
    @ValueMapValue
    @Default(values = "Default Description")
    private String description;

    @Getter
    private String resourcePath;

    @PostConstruct
    protected void init(){
        logger.debug("model test is working");
        description = description != null ? description.toUpperCase() : "error editing name field";
        logger.debug("name field value: {}", description);

        Resource resource = request.getResource();
        resourcePath = resource.getPath();
        logger.debug("resource path value: {}", resourcePath);
        logger.debug("resource path not worked");
        }
}
