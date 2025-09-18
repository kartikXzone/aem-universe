package universe.core.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import universe.core.services.OpenAIConnector;

@Component(service = OpenAIConnector.class, immediate = true)
@Designate(ocd = OpenAIConnectorImpl.Config.class)
public class OpenAIConnectorImpl implements OpenAIConnector {

    public static final Logger log = LoggerFactory.getLogger(OpenAIConnectorImpl.class);

    private String apiKey;
    private String apiUrl;

    @ObjectClassDefinition(name = "OpenAI API Config", description = "Configuration for OpenAI integration")
    public @interface Config {

        @AttributeDefinition(name = "OpenAI Key", description = "Enter the secret API key")
        String openAIKey();

        @AttributeDefinition(name = "OpenAI URL", description = "Enter the OpenAI URL")
        String openAIURL();
    }

    @Activate
    @Modified
    protected void activate(Config config) {
        this.apiKey = config.openAIKey();
        this.apiUrl = config.openAIURL();
    }

    public String getOpenAiResult(String prompt) {

        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setHeader("Authorization", "Bearer " + apiKey);
            httpPost.setHeader("Content-Type", "application/json");

            // Build JSON body
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode requestJson = objectMapper.createObjectNode();
            requestJson.put("model", "gpt-3.5-turbo");

            ArrayNode messages = objectMapper.createArrayNode();
            ObjectNode userMsg = objectMapper.createObjectNode();
            userMsg.put("role", "user");
            userMsg.put("content", prompt);
            messages.add(userMsg);

            requestJson.set("messages", messages);
            StringEntity entity = new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity httpEntityResponse = httpResponse.getEntity();
            String responseEntity = EntityUtils.toString(httpEntityResponse);

            JsonNode jsonNode = objectMapper.readTree(responseEntity);

            if (statusCode == 200 && jsonNode.has("choices")) {
                return jsonNode.path("choices").get(0).path("message").path("content").asText();
            } else if (jsonNode.has("error")) {
                String errorMessage = jsonNode.path("error").path("message").asText();
                log.error("OpenAI API returned error: {}", errorMessage);
                return "OpenAI Error: " + errorMessage;
            } else {
                return "Unexpected response from OpenAI.";
            }

        } catch (Exception e) {
            log.error("Error calling OpenAI API", e);
            return "Error processing your request.";
        }
    }
}
