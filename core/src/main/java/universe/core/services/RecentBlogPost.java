package universe.core.services;

import org.apache.sling.api.resource.ResourceResolver;
import universe.core.beans.RecentBlogPostBean;
import java.util.List;

public interface RecentBlogPost {

    public List<RecentBlogPostBean> recentBlogPost(String blogPath, String blogTag, ResourceResolver resourceResolver);
}
