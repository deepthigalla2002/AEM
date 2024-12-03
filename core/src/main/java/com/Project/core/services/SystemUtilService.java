package com.Project.core.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = SystemUtilService.class)
public class SystemUtilService {

    @Reference
    ResourceResolverFactory factory;

    public ResourceResolver getResourceResolver() {
        ResourceResolver resolver = null;
        try {
            Map<String, Object> props = new HashMap<>();
            props.put(ResourceResolverFactory.SUBSERVICE, "systemsubservice");
            resolver = factory.getServiceResourceResolver(props);
            ;

        } catch (LoginException e) {
            e.printStackTrace();
        }
        return resolver;
    }

}
