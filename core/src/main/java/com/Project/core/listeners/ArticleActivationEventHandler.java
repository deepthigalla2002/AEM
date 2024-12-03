package com.Project.core.listeners;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Project.core.services.SystemUtilService;
import com.day.cq.replication.ReplicationAction;

@Component(service = EventHandler.class, property = { EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC,
        EventConstants.EVENT_FILTER + "=(&(type=ACTIVATE)(paths=/content/Project/us/en/*))" })
public class ArticleActivationEventHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleActivationEventHandler.class);

    @Reference
    SystemUtilService systemUtilService;

    @Override
    public void handleEvent(Event event) {

        LOG.info("inside handle event method");

        // String[] properties = event.getPropertyNames();
        // for (String property : properties) {
        // LOG.info("Property Name - {},Property Value ={}", property,
        // event.getProperty(property));
        // }

        String[] paths = (String[]) event.getProperty("paths");

        for (String path : paths) {
            ResourceResolver resolver = systemUtilService.getResourceResolver();
            Resource contentResource = resolver.getResource(path + "/jcr:content");
            ModifiableValueMap mProp = contentResource.adaptTo(ModifiableValueMap.class);
            mProp.put("pageActivated", true);
            try {
                resolver.commit();
                resolver.close();
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        }
    }

}
