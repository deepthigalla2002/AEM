package com.Project.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = WorkflowProcess.class, property = {
        "process.label=Custom Workflow Process"
})
public class CustomWorkflow implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(CustomWorkflow.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
            throws WorkflowException {
        // Retrieve the payload path (node being processed)
        String payloadPath = workItem.getWorkflowData().getPayload().toString();
        LOG.info("Executing workflow for payload: {}", payloadPath);

        // Adapt the workflow session to a ResourceResolver
        ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);
        if (resourceResolver != null) {
            try {
                // Access the payload resource
                Resource resource = resourceResolver.getResource(payloadPath);
                if (resource != null) {
                    // Modify properties on the resource
                    ModifiableValueMap properties = resource.adaptTo(ModifiableValueMap.class);
                    if (properties != null) {
                        properties.put("customWorkflowProperty", "Workflow Triggered");
                        resourceResolver.commit();
                        LOG.info("Property 'customWorkflowProperty' updated on node: {}", payloadPath);
                    } else {
                        LOG.warn("Unable to adapt resource to ModifiableValueMap: {}", payloadPath);
                    }
                } else {
                    LOG.warn("Resource not found for payload path: {}", payloadPath);
                }
            } catch (Exception e) {
                LOG.error("Error processing workflow for payload: {}", payloadPath, e);
                throw new WorkflowException("Failed to process workflow", e);
            }
        } else {
            LOG.error("Unable to adapt WorkflowSession to ResourceResolver");
            throw new WorkflowException("ResourceResolver adaptation failed");
        }
    }
}
