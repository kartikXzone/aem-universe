package universe.core.services.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import universe.core.beans.RecentBlogPostBean;
import universe.core.constants.SearchConstants;
import universe.core.services.RecentBlogPost;

import javax.jcr.Session;
import java.util.*;

@Component(service = RecentBlogPost.class, immediate = true)
public class RecentBlogPostImpl implements RecentBlogPost {

    public static final Logger log = LoggerFactory.getLogger(RecentBlogPostImpl.class);

    @Reference
    private QueryBuilder queryBuilder;

    @Override
    public List<RecentBlogPostBean> recentBlogPost(String blogPath, String blogTag, ResourceResolver resourceResolver) {

        Session session = resourceResolver.adaptTo(Session.class);
        List<RecentBlogPostBean> recentBlogPostBeanList = new ArrayList<>();

        try {
            Map<String, String> predicates = new HashMap<>();

            predicates.put("path", blogPath);
            predicates.put("type", SearchConstants.PAGE_TYPE);
            predicates.put("1_property", SearchConstants.TAG_TYPE);
            predicates.put("1_property.value", blogTag);
            predicates.put("orderby", SearchConstants.ORDER_BY);
            predicates.put("orderby.sort", SearchConstants.ORDER_BY_SORT);
            predicates.put("p.limit", "-1");

            Query query = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
            SearchResult searchResult = query.getResult();

            if (searchResult != null) {
                searchResult.getResources().forEachRemaining(item -> {
                    Page page = item.adaptTo(Page.class);
                    if (page != null && page.hasContent()) {
                        String pageTitle = page.getTitle() != null ? page.getTitle() : StringUtils.EMPTY;
                        String pageDescription = page.getDescription() != null ? page.getDescription() : StringUtils.EMPTY;
                        String pageLink = page.getPath(); // adding .html extension into the HTL for reusability and flexibility.

                        // getting page thumbnail image
                        String thumbnailImage = null;
                        String pagePath = page.getPath().concat("/jcr:content/image");
                        Resource resource = resourceResolver.getResource(pagePath);
                        if (resource != null) {
                            ValueMap thumbnailImageValuemap = resource.getValueMap();
                            thumbnailImage = thumbnailImageValuemap.get("fileReference", String.class);
                        }

                        // getting page tags
                        Tag[] pageTags = page.getTags();
                        String tagTitle = null;
                        if (pageTags != null) {
                            for (Tag tag : pageTags) {
                                if (tag != null) {
                                    tagTitle = tag.getTitle();
                                }
                            }
                        }

                        RecentBlogPostBean recentBlogPostBean = new RecentBlogPostBean(pageTitle, pageDescription, tagTitle, pageLink, thumbnailImage);
                        recentBlogPostBeanList.add(recentBlogPostBean);
                    }
                });
            }
        } catch (Exception exception) {
            log.debug("exception handled");
        }
        return recentBlogPostBeanList;
    }
}