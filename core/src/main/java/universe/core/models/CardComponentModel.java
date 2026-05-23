package universe.core.models;

import com.day.cq.wcm.api.PageManager;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.bson.codecs.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import java.util.Optional;
import java.util.Random;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class CardComponentModel {

    private static final Logger log = LoggerFactory.getLogger(CardComponentModel.class);

    @ScriptVariable
    ValueMap pageProperties;

    @Getter
    @ValueMapValue
    @Default(values = "0000")
    private int id;

    @Getter
    @ValueMapValue
    @Default(values = "name")
    private String name;

    @Getter
    @ValueMapValue
    private String gender;

    @Getter
    @ValueMapValue
    private String address;

    @Getter
    String pageValue;

    @PostConstruct
    protected void init() {

        name = !name.isEmpty() ? name.toUpperCase() : StringUtils.EMPTY;
        gender = !gender.isEmpty() ? gender.toLowerCase() : StringUtils.EMPTY;
        address = !address.isEmpty() ? address.toLowerCase() : StringUtils.EMPTY;
        pageValue = pageProperties.get("jcr:title", StringUtils.EMPTY);

    }
}
