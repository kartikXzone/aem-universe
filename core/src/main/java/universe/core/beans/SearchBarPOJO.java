package universe.core.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SearchBarPOJO {

    public String pagePath;
    public String pageTitle;

    public SearchBarPOJO(String pagePath, String pageTitle) {
        this.pagePath = pagePath;
        this.pageTitle = pageTitle;
    }

}
