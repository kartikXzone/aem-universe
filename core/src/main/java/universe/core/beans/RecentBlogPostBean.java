package universe.core.beans;

import java.util.List;

public class RecentBlogPostBean {

    public String pageTitle;
    public String pageDescription;
    public String pageTags;
    public String pageLink;
    public String thumbnailImage;

    public RecentBlogPostBean(String pageTitle, String pageDescription, String pageTags, String pageLink, String thumbnailImage) {
        this.pageTitle = pageTitle;
        this.pageDescription = pageDescription;
        this.pageTags = pageTags;
        this.pageLink = pageLink;
        this.thumbnailImage = thumbnailImage;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getPageDescription() {
        return pageDescription;
    }

    public String getPageTags() {
        return pageTags;
    }

    public String getPageLink() {
        return pageLink;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }
}