package com.Project.core.schedulers;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service=Runnable.class) 
@Designate(ocd=OsgiServiceConfiguration.class)
public class SlingSchedulerExample implements Runnable{

   private static final Logger log= LoggerFactory.getLogger(SlingSchedulerExample.class);

    @Reference
    Scheduler scheduler;

    public int schedulerName;

    @Activate
    public void init(OsgiServiceConfiguration config)
    {
        addScheduler(config);
    }

    public void addScheduler(OsgiServiceConfiguration config)
    {
        if(config.enableScheduler()){
        schedulerName=config.serviceName().hashCode();
        ScheduleOptions so=scheduler.EXPR(config.cronExpression());
        scheduler.schedule(this,so);
        //ScheduleOptions name=name(config.serviceName());
        }
        else
        {
            log.error("its not enabled");
        }
    }

    @Deactivate
    public void destroy(OsgiServiceConfiguration config)
    {
        deactive(config);
    }
    public void deactive(OsgiServiceConfiguration config)
    {
        scheduler.unschedule(config.serviceName());
        
    }

    @Override
    public void run()
    {

        log.info("SSE Executed succesfully");

    }
}
