package universe.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import universe.core.services.SportActivitiesInterface;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SportsModel {

    private static final Logger logger = LoggerFactory.getLogger(SportsModel.class);

    @OSGiService
    SportActivitiesInterface sportActivitiesInterface;

    private String result;

    @PostConstruct
    protected void init() {
        logger.debug("logger 1 is started: ");
        result = sportActivitiesInterface.getSport();
        logger.debug("result: {}", result);
    }

    public String getResult() {
        return result;
    }
}
