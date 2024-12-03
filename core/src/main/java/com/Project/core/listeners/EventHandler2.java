package com.Project.core.listeners;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

@Component(immediate = true, service = EventHandler.class, property = {
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
        EventConstants.EVENT_FILTER + "=(path=/content/*)"
})
public class EventHandler2 implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandler2.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private static final String WORKFLOW_MODEL_PATH = "/var/workflow/models/expirydateworkflowmodel";

    @Override
    public void handleEvent(Event event) {
        String path = (String) event.getProperty("path");
        if (path == null || path.isEmpty()) {
            LOG.warn("Received event with null or empty path");
            return;
        }
        LOG.info("Resource added event received for path: {}", path);

        if (path.startsWith("/content")) {
            try (ResourceResolver resolver = getServiceResourceResolver()) {
                Resource resource = resolver.getResource(path);
                if (resource != null) {
                    LOG.info("Resource exists at path: {}", path);
                    initiateWorkflow(resource, resolver);
                } else {
                    LOG.warn("No resource found at path: {}", path);
                }
            } catch (Exception e) {
                LOG.error("Error handling resource added event for path: {}", path, e);
            }
        }
    }

    private ResourceResolver getServiceResourceResolver() throws Exception {
        Map<String, Object> params = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "workflowservice");
        return resourceResolverFactory.getServiceResourceResolver(params);
    }

    private void initiateWorkflow(Resource resource, ResourceResolver resolver) {
        try {
            com.adobe.granite.workflow.WorkflowSession workflowSession = resolver
                    .adaptTo(com.adobe.granite.workflow.WorkflowSession.class);
            if (workflowSession != null) {
                com.adobe.granite.workflow.model.WorkflowModel model = workflowSession.getModel(WORKFLOW_MODEL_PATH);
                if (model != null) {
                    workflowSession.startWorkflow(model,
                            workflowSession.newWorkflowData("JCR_PATH", resource.getPath()));
                    LOG.info("Workflow initiated for resource: {}", resource.getPath());
                } else {
                    LOG.warn("Workflow model not found: {}", WORKFLOW_MODEL_PATH);
                }
            }
        } catch (Exception e) {
            LOG.error("Error initiating workflow for resource: {}", resource.getPath(), e);
        }
    }
}
