//package universe.core.models;
//
//import com.day.cq.wcm.api.Page;
//import com.day.cq.wcm.api.Template;
//import com.day.cq.wcm.api.components.Component;
//import org.apache.sling.api.SlingHttpServletRequest;
//import org.apache.sling.api.resource.Resource;
//import org.apache.sling.api.resource.ResourceResolver;
//import org.apache.sling.api.resource.ValueMap;
//import org.apache.sling.models.annotations.DefaultInjectionStrategy;
//import org.apache.sling.models.annotations.Model;
//import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
//import org.apache.sling.models.annotations.injectorspecific.SlingObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.PostConstruct;
//import javax.jcr.Node;
//import javax.jcr.RepositoryException;
//import javax.jcr.Session;
//import java.util.Map;
//
//@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
//
//public class JAVA_annotationsModel {
//
//    private static final Logger log = LoggerFactory.getLogger(JAVA_annotationsModel.class);
//
//    @SlingObject
//    private org.apache.sling.api.resource.Resource resource;
//
//    @ScriptVariable
//    private Component component;
//
//    @ScriptVariable
//    private Node currentNode;
//    private Node addedNode;
//
//    @ScriptVariable
//    private Page currentPage;
//    private Page pageCreation;
//
//    @SlingObject
//    private ResourceResolver resourceResolver;
//
//    @ScriptVariable
//    private Template template;
//
//    public void addNodeMethod(Session session) throws RepositoryException {
//
//        // getting parent node
//        Node parentNode = session.getNode("/content/items/column/items");
//
//        // adding child node to the parent node
//        addedNode = parentNode.addNode("newNode", "nt:unstructured");
//
//        // setting node properties
//        addedNode.setProperty("fieldLabel", "image");
//        session.save();
//    }
//
//    @PostConstruct
//    protected void init() {
//
//        try {
//
//            // ResourceResolver Sling Object
//            org.apache.sling.api.resource.Resource resourcePath = resourceResolver.getResource("/content/universe-project/us/en/centre-page/jcr:content/root/container/container/details");
//            if (resourcePath != null) {
//                String stringResource = resourcePath.getPath();
//                log.debug("resource resolver path: {}", stringResource);
//            } else {
//                log.info("resource resolver did not worked!");
//            }
//
//
//            // Page Sling Object
//            log.debug("absolute parent: {}, content resource: {}, language.getCountry: {}, last modified.getCalenderType : {}, page properties: {}, page manager: {}, list children: {}",
//                    currentPage.getAbsoluteParent(1), currentPage.getContentResource(), currentPage.getLanguage().getCountry(),
//                    currentPage.getLastModified().getCalendarType(), currentPage.getProperties(), currentPage.getPageManager(),
//                    currentPage.listChildren());
//
//
//            // page creation using PageManager
//            pageCreation = currentPage.getPageManager().create("/content/universe-project/us/en/centre-page/solar-system/moon",
//                    "earth-moon", "/conf/universe-project/settings/wcm/templates/page-content", "Earth Moon");
//            log.debug("page created: {}", pageCreation.getPath());
//
//
//            // Template Sling Object
//            ValueMap valueMap = template.getProperties();
//            if (!valueMap.isEmpty()) {
//                for (Map.Entry<String, Object> map : valueMap.entrySet()) {
//                    log.debug("map value: {}, map key: {}", map.getValue(), map.getKey());
//                }
//            } else {
//                log.info("template valuemap is empty");
//            }
//
//
//            Template templateDetail = currentPage.getTemplate();
//            if (templateDetail != null && templateDetail.getDescription() != null) {
//                log.debug("Template description value: {}", templateDetail.getDescription());
//            } else {
//                log.info("No template description available.");
//            }
//
//
//            // Component Sling Object
//            log.debug("Component cell name :{} , componentGroup: {}, defaultView: {}, childEditConfig: {}, designDialogPath: {}, dialogPath: {} , editConfig: {}",
//                    component.getCellName(), component.getComponentGroup(), component.getDefaultView(), component.getChildEditConfig(),
//                    component.getDesignDialogPath(), component.getDialogPath(), component.getEditConfig());
//
//
//            // Node SLing Object
//            if (currentNode != null) {
//                addNodeMethod(currentNode.getSession());
//                log.error("Current node is working fine...");
//            }
//            log.debug("Node added at path: {}", addedNode.getPath());
//
//        } catch (Exception exception) {
//
//        }
//    }
//}
