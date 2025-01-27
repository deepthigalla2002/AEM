package com.Project.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;

import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Practice OCD Configuration", description = "Practice OCD Configuration description")
public @interface PracticeServiceOCDConfiguration {

    @AttributeDefinition(name = "String Config Example Name", description = "String Config Example Description", type = AttributeType.STRING)
    String name() default "Test";

    @AttributeDefinition(name = "String[] Config Example Name", description = "String[] Config Example Description", type = AttributeType.STRING)
    String[] supported_countries() default { "India", "United States" };

}
