package com.Project.core.workflows;

//Task :12
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component(service = WorkflowProcess.class, property = { "process.label=Page Modification Logger Process" })
public class PageModificationLoggerProcess implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(PageModificationLoggerProcess.class);
    private static final String SERVICE_USER = "ExpriyDateWorkFlow";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void execute(WorkItem workItem, WorkflowSession aworkflowSessionrg1, MetaDataMap metaDataMap)
            throws WorkflowException {
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, SERVICE_USER);

        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(param)) {
            String pagePath = workItem.getWorkflowData().getPayload().toString();

            Resource pageResource = resourceResolver.getResource(pagePath);
            if (pageResource != null) {
                LOG.info("Page has been created or modified at path: {}", pagePath);
            } else {
                LOG.warn("Could not find the page resource at path: {}", pagePath);
            }
        } catch (LoginException e) {
            LOG.error("Unable to get Service Resource Resolver: {}", e.getMessage());
        } catch (Exception e) {
            LOG.error("Unexpected error in PageModificationLoggerProcess: {}", e.getMessage());
        }
    }
}
