package com.Project.core.schedulers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;

@Component(service = Runnable.class, immediate = true)
@Designate(ocd = MyScheduler.Config.class)
public class MyScheduler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(MyScheduler.class);

    @ObjectClassDefinition(name = "Custom AEM Scheduler")
    public @interface Config {
        @AttributeDefinition(name = "Cron Expression", description = "Enter the cron expression")
        String scheduler_cron_expression() default "0 0/2 * 1/1 * ? *";

    }

    @Reference
    private Scheduler scheduler;

    private String cronExpression;
    private String pagePath = "/content";

    @Reference
    private Replicator replicator;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Activate
    protected void activate(Config config) {
        this.cronExpression = config.scheduler_cron_expression();
        ScheduleOptions options = scheduler.EXPR(cronExpression);
        options.name("My Custom Scheduler");
        options.canRunConcurrently(false);

        scheduler.schedule(this, options);
        LOG.info("Scheduler activated with cron expression: {}", cronExpression);
    }

    @Override
    public void run() {
        LOG.info("Scheduler triggered. Attempting to publish child pages under: {}", pagePath);
        try (ResourceResolver resourceResolver = getServiceResourceResolver()) {
            Session session = resourceResolver.adaptTo(Session.class);
            if (session == null) {
                LOG.error("Session could not be obtained. Replication aborted.");
                return;
            }
            Resource rootResource = resourceResolver.getResource(pagePath);
            if (rootResource == null) {
                LOG.error("Page path {} does not exist. Aborting replication.", pagePath);
                return;
            }
            publishChildPages(rootResource, session);
            LOG.info("All child pages under {} have been successfully published.", pagePath);
        } catch (Exception e) {
            LOG.error("Error occurred while publishing the page: {}", pagePath, e);
        }
    }

    private void publishChildPages(Resource resource, Session session) {
        try {
            LOG.info("Publishing page: {}", resource.getPath());
            replicator.replicate(session, ReplicationActionType.ACTIVATE, resource.getPath());

            Iterator<Resource> children = resource.listChildren();
            while (children.hasNext()) {
                Resource child = children.next();
                publishChildPages(child, session);
            }
        } catch (ReplicationException e) {
            LOG.error("Error publishing page: {}", resource.getPath(), e);
        }
    }

    private ResourceResolver getServiceResourceResolver() throws Exception {

        Map<String, Object> authInfo = new HashMap<>();
        authInfo.put(ResourceResolverFactory.SUBSERVICE, "systemsubservice");
        return resourceResolverFactory.getServiceResourceResolver(authInfo);
    }
}
