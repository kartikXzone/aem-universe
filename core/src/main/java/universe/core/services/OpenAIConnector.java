package universe.core.services;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface OpenAIConnector {

    public String getOpenAiResult(String stringValue);
}
