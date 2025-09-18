package universe.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import universe.core.beans.RecentBlogPostBean;
import universe.core.services.RecentBlogPost;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class RecentBlogPostModel {

    public static final Logger logger = LoggerFactory.getLogger(RecentBlogPostModel.class);

    @Self
    SlingHttpServletRequest request;

    @ValueMapValue
    private String blogPath;

    @ValueMapValue
    private String blogTag;

    @OSGiService
    RecentBlogPost recentBlogPost;

    @Getter
    private List<RecentBlogPostBean> recentBlogPostList;

    final int BLOG_COUNT = 3;

    @PostConstruct
    protected void init() {
        ResourceResolver resourceResolver = request.getResourceResolver();
        recentBlogPostList = new ArrayList<>();

        if (blogPath != null) {
            recentBlogPostList = recentBlogPost.recentBlogPost(blogPath, blogTag, resourceResolver);
            recentBlogPostList = recentBlogPostList.stream().limit(BLOG_COUNT).collect(Collectors.toList());
        }
    }
}
