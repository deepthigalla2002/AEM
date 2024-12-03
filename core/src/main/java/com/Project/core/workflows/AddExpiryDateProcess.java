package com.Project.core.workflows;

import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

@Component(service = WorkflowProcess.class, property = {
        "process.label=Add Expiry Date Workflow Step"
})
public class AddExpiryDateProcess implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(AddExpiryDateProcess.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void execute(WorkItem workItem, com.day.cq.workflow.WorkflowSession workflowSession,
            MetaDataMap metaDataMap) {
        String payloadPath = workItem.getWorkflowData().getPayload().toString();
        LOG.info("Starting workflow process for payload: {}", payloadPath);

        try {
            Map<String, Object> serviceParams = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
                    "workflowservice");
            try (ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(serviceParams)) {
                Resource resource = resolver.getResource(payloadPath);
                if (resource != null) {
                    LOG.info("Resource found at: {}", payloadPath);

                    Node node = resource.adaptTo(Node.class);
                    if (node != null) {
                        Calendar currentTime = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        String expiryDate = sdf.format(currentTime.getTime());

                        node.setProperty("expiry", expiryDate);
                        resolver.commit();

                        LOG.info("Expiry property set for node at: {} with value: {}", payloadPath, expiryDate);
                    } else {
                        LOG.warn("Failed to adapt resource to a JCR node: {}", payloadPath);
                    }
                } else {
                    LOG.warn("No resource found at payload path: {}", payloadPath);
                }
            }
        } catch (Exception e) {
            LOG.error("Error while processing workflow for payload: {}", payloadPath, e);
        }
    }
}
