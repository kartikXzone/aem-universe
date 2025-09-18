package universe.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import org.apache.sling.models.annotations.injectorspecific.SlingObject;
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

    @Getter
    @Inject
    private Integer id;

    @Inject
    private String name;

    @Inject
    private String gender;

    @Inject
    private String address;

    @Getter
    private String detailsResult;

    @PostConstruct
    protected void init() {

        log.debug("first logger - model is working fine");

        if (id == null) {
            Random random = new Random();
            id = random.nextInt();
        }

        detailsResult = (name != null ? "Name: " + name : " please provide the name ") +
                (gender != null ? ", Gender: " + gender : " please provide the gender ") +
                (address != null ? ", Address: " + address : " please provide the address ");

        log.debug("details result: {}", detailsResult);
    }
}
