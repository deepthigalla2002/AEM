package com.Project.core.handler;

//Task 12:
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.model.WorkflowModel;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component(immediate = true, service = EventHandler.class, property = {
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/ResourceAdded",
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/ResourceChanged",
        EventConstants.EVENT_FILTER + "=(path=/content/Demo/us/en/sam/*)"
})
public class PageEventHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PageEventHandler.class);
    private static final String WORKFLOW_MODEL_PATH = "/var/workflow/models/launcher";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private JobManager jobManager;

    @Override
    public void handleEvent(org.osgi.service.event.Event event) {
        String path = (String) event.getProperty("path");

        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, "ExpriyDateWorkFlow");

        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(param)) {
            WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);
            WorkflowModel workflowModel = workflowSession.getModel(WORKFLOW_MODEL_PATH);

            if (workflowModel != null) {
                Map<String, Object> workflowData = new HashMap<>();
                workflowData.put("payload", path);

                workflowSession.startWorkflow(workflowModel, workflowSession.newWorkflowData("JCR_PATH", path));
                LOG.info("Triggered workflow for page event at path: {}", path);
            } else {
                LOG.error("Workflow model not found at path: {}", WORKFLOW_MODEL_PATH);
            }

        } catch (LoginException e) {
            LOG.error("Failed to get Service Resource Resolver: {}", e.getMessage());
        } catch (Exception e) {
            LOG.error("Failed to trigger workflow: {}", e.getMessage());
        }
    }
}
