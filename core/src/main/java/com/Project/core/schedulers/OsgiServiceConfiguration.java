package com.Project.core.schedulers;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="surge software configuration",description="this is new config")
public @interface OsgiServiceConfiguration {

    @AttributeDefinition(
        name="Service Name",description="Enter Service Name",type=AttributeType.STRING)
    public String serviceName() default "Practice";

    @AttributeDefinition(name="canRunConcurrent",description="Please Select the Checkbox",type=AttributeType.BOOLEAN)
    public boolean canRunConcurrent();
    
    @AttributeDefinition(name="Cron Expression",description="Enter Cron BasedExpression",type=AttributeType.STRING)
    public String cronExpression() default "0 0/1 * 1/1 * ? *";

    @AttributeDefinition(name="Enable Scheduler",type=AttributeType.BOOLEAN)
    public boolean enableScheduler() default true;
}
