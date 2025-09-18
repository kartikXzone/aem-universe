package universe.core.models;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import universe.core.beans.details.AddressBean;
import universe.core.beans.details.DetailsBean;
import universe.core.beans.details.ParentDetailsBean;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DetailsModel {

    private static final Logger log = LoggerFactory.getLogger(DetailsModel.class);

    @ChildResource(name = "selfDetails")
    private Resource selfDetails;

    @Getter
    private List<DetailsBean> detailsBeanList;

    @PostConstruct
    protected void init() {
        detailsBeanList = new ArrayList<>();

        if (selfDetails != null && selfDetails.hasChildren()) {
            selfDetails.listChildren().forEachRemaining(selfItem -> {
                ValueMap valueMap = selfItem.getValueMap();
                String firstName = valueMap.get("firstName", StringUtils.EMPTY);
                String lastName = valueMap.get("lastName", StringUtils.EMPTY);

                List<ParentDetailsBean> parentDetailslist = new ArrayList<>();
                Resource parentsDetailResource = selfItem.getChild("parentsDetail");

                // retrieving data from the second level - multifield
                if (parentsDetailResource != null && parentsDetailResource.hasChildren()) {
                    parentsDetailResource.listChildren().forEachRemaining(parentItem -> {
                        ValueMap parentItemValueMap = parentItem.getValueMap();
                        String fatherName = parentItemValueMap.get("fatherName", StringUtils.EMPTY);
                        String motherName = parentItemValueMap.get("motherName", StringUtils.EMPTY);

                        List<AddressBean> addresslist = new ArrayList<>();
                        Resource addressResource = parentItem.getChild("address");

                        // retrieving data from the third level - multifield
                        if (addressResource != null && addressResource.hasChildren()) {
                            addressResource.listChildren().forEachRemaining(addressItem -> {
                                ValueMap map = addressItem.getValueMap();
                                String fullAddress = map.get("fullAddress", StringUtils.EMPTY);
                                addresslist.add(new AddressBean(fullAddress));
                            });
                        }
                        parentDetailslist.add(new ParentDetailsBean(fatherName, motherName, addresslist));
                    });
                }
                detailsBeanList.add(new DetailsBean(firstName, lastName, parentDetailslist));
            });
        }
    }
}
