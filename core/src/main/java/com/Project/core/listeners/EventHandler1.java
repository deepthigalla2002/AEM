package com.Project.core.listeners;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationEvent;

import java.util.Collections;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = EventHandler.class, immediate = true, property = {
        EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC })
public class EventHandler1 implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandler1.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void handleEvent(Event event) {
        try {
            ReplicationAction replicationAction = ReplicationAction.fromEvent(event);

            if (replicationAction != null && ReplicationActionType.ACTIVATE.equals(replicationAction.getType())) {

                String publishedPagePath = replicationAction.getPath();
                LOG.info("Page published: {}", publishedPagePath);

                updatePageProperty(publishedPagePath);
            }
        } catch (Exception e) {
            LOG.error("Error in handling replication event: {}", e.getMessage(), e);
        }
    }

    private void updatePageProperty(String pagePath) {
        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(
                Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "eventhandler"))) {

            Resource pageResource = resourceResolver.getResource(pagePath + "/jcr:content");
            if (pageResource == null) {
                LOG.warn("Does not have jcr:content: {}", pagePath);
                return;
            }
            if (pageResource != null) {
                ModifiableValueMap properties = pageResource.adaptTo(ModifiableValueMap.class);
                if (properties != null) {
                    properties.put("changed", true);
                    resourceResolver.commit();
                    LOG.info("Property 'changed=true' added to page: {}", pagePath);
                } else {
                    LOG.warn("Unable to adapt resource to ModifiableValueMap: {}", pagePath);
                }
            }
        } catch (PersistenceException e) {
            LOG.error("Error while committing changes to page: {}", pagePath, e);
        } catch (Exception e) {
            LOG.error("Unexpected error while updating page property: {}", pagePath, e);
        }
    }

}
