package universe.core.services;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.annotation.versioning.ProviderType;
import universe.core.beans.SearchBarPOJO;

import java.util.List;

@ProviderType
public interface SearchBarInterface {

    public List<SearchBarPOJO> getSearchResult(String pagePath, String pageTitle, String searchTerm, ResourceResolver resourceResolver);
}
