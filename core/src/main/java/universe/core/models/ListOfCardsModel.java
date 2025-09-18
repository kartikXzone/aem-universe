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
import universe.core.beans.ListOfCardsBean;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ListOfCardsModel {

    private final static Logger log = LoggerFactory.getLogger(ListOfCardsModel.class);

    @ChildResource
    public Resource cards;

    @Getter
    private List<ListOfCardsBean> beanList;

    @PostConstruct
    protected void init() {

        beanList = new ArrayList<>();

        if (cards != null) {
            cards.listChildren().forEachRemaining(item -> {
                ValueMap valueMap = item.getValueMap();

                String altTextValueMap = valueMap.get("altText", StringUtils.EMPTY);
                String fileReferenceValueMap = valueMap.get("fileReference", StringUtils.EMPTY);
                String cardTitleValueMap = valueMap.get("cardTitle", StringUtils.EMPTY);
                String cardDescriptionValueMap = valueMap.get("cardDescription", StringUtils.EMPTY);
                String ctaLabelValueMap = valueMap.get("ctaLabel", StringUtils.EMPTY);
                String linkValueMap = valueMap.get("link", StringUtils.EMPTY);
                String linkTargetValueMap = valueMap.get("linkTarget", StringUtils.EMPTY);

                ListOfCardsBean listOfCardsBean = new ListOfCardsBean(fileReferenceValueMap, altTextValueMap, cardTitleValueMap, cardDescriptionValueMap, ctaLabelValueMap, linkValueMap, linkTargetValueMap);
                beanList.add(listOfCardsBean);
            });
        }
    }
}
