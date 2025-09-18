package universe.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MultipleEntryComponentModel {

    @ChildResource
    @Named("container")
    private Resource childResource;

    private List<String> arrayList;
    private int componentCount;

    @PostConstruct
    protected void init() {

        arrayList = new ArrayList<>();

        if (childResource != null && childResource.hasChildren()) {
            childResource.listChildren().forEachRemaining(item -> {
                String resourceType = item.getValueMap().get("sling:resourceType", StringUtils.EMPTY);
                arrayList.add(resourceType);
            });
        }
        componentCount = arrayList.size();
    }

    public List<String> getArrayList() {
        return arrayList;
    }

    public int getComponentCount() {
        return componentCount;
    }
}
