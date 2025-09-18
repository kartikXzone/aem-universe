package universe.core.services.impl;

import com.adobe.xfa.Bool;
import com.day.cq.search.QueryBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import universe.core.services.SportActivitiesInterface;

import java.io.StringReader;
import java.util.Iterator;

import static com.adobe.acs.commons.models.injectors.impl.InjectorUtils.getResourceResolver;

@Component(service = SportActivitiesInterface.class, immediate = true)
@Designate(ocd = SportActivitiesImpl.config.class)

public class SportActivitiesImpl implements SportActivitiesInterface {

    private static final Logger log = LoggerFactory.getLogger(SportActivitiesImpl.class);

    @ObjectClassDefinition(
            name = "Sport OSGi Configuration",
            description = "This is OSGi sports configuration"
    )

    @interface config{

        @AttributeDefinition(name = "Enter Sport Name"  , description = "provide sport name")
        String sportName() ;

        @AttributeDefinition(name = "Sport Origin Country")
        String sportOrigin() default "India";

        @AttributeDefinition(name = "Players Count")
        int playersCount() default 4;
    }

    public final String getSport() {
        log.debug("the value is coming...");
        return "football".toUpperCase();
    }


    public int getSS() {
        return 0;
    }

//
//    @Activate
//    public void activate(){
//        log.debug("activate annotation is working...");
//    }


}
