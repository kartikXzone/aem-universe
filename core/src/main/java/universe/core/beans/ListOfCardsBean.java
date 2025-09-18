package universe.core.beans;

import lombok.Getter;
import universe.core.Utils.LinkUtil;

public class ListOfCardsBean {

    public String fileReference;
    public String altText;
    public String cardTitle;
    public String cardDescription;
    public String ctaLabel;
    //        return LinkUtil.getFormattedURL(link);
    @Getter
    public String link;
    public String linkTarget;

    public ListOfCardsBean(String fileReference, String altText, String cardTitle, String cardDescription, String ctaLabel, String link, String linkTarget) {
        this.fileReference = fileReference;
        this.altText = altText;
        this.cardTitle = cardTitle;
        this.cardDescription = cardDescription;
        this.ctaLabel = ctaLabel;
        this.link = link;
        this.linkTarget = linkTarget;
    }
}
