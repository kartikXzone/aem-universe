package universe.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import universe.core.mULTIFIELD.singleMultiFieldBean;

import java.util.List;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class newMultiField {

@ChildResource
    List<singleMultiFieldBean>singleMultiField;

    public List<singleMultiFieldBean> getSingleMultiField() {
        return singleMultiField;
    }
}