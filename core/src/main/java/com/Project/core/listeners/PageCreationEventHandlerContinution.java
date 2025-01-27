package com.Project.core.listeners;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.model.WorkflowModel;

import javax.jcr.Session;
import java.util.Collections;

@Component(service = EventHandler.class, immediate = true, property = {
        "event.topics=org/apache/sling/api/resource/Resource/ADDED",
        "event.filter=(path=/content/*)"
})
public class PageCreationEventHandlerContinution implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PageCreationEventHandlerContinution.class);

    private static final String WORKFLOW_PATH = "/var/workflow/models/expirydateadd";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private WorkflowService workflowService;

    @Override
    public void handleEvent(Event event) {
        String resourcePath = (String) event.getProperty("path");
        LOG.info("Detected page creation event for path: {}", resourcePath);

        try (ResourceResolver resolver = getServiceResourceResolver()) {
            Resource resource = resolver.getResource(resourcePath);
            if (resource != null) {
                startWorkflowForPage(resolver, resourcePath);
            } else {
                LOG.warn("Resource not found for path: {}", resourcePath);
            }
        } catch (Exception e) {
            LOG.error("Error handling page creation event for path: {}", resourcePath, e);
        }
    }

    private void startWorkflowForPage(ResourceResolver resolver, String resourcePath) throws WorkflowException {
        Session session = resolver.adaptTo(Session.class);
        if (session != null) {
            WorkflowSession workflowSession = workflowService.getWorkflowSession(session);
            WorkflowModel workflowModel = workflowSession.getModel(WORKFLOW_PATH);

            if (workflowModel != null) {
                workflowSession.startWorkflow(
                        workflowModel,
                        workflowSession.newWorkflowData("JCR_PATH", resourcePath));
                LOG.info("Workflow {} started for page at path: {}", WORKFLOW_PATH, resourcePath);
            } else {
                LOG.error("Workflow model not found at path: {}", WORKFLOW_PATH);
            }
        } else {
            LOG.error("Unable to obtain JCR Session from ResourceResolver.");
        }
    }

    private ResourceResolver getServiceResourceResolver() throws Exception {
        return resourceResolverFactory.getServiceResourceResolver(
                Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "ExpriyDateWorkFlow"));
    }
}
