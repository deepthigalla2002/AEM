package com.Project.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(name="Student Details",description="Taking data from the student")
public @interface StudentConfiguration1 {

    @AttributeDefinition(name="Student Name",type=AttributeType.STRING,description="Enter Student Name")
    public String getStudentName() default "Deepthi";


    @AttributeDefinition(name="Roll Number",description="Enter Roll Number Here",type=AttributeType.INTEGER)
    public int getRollNumber() default 1;

    @AttributeDefinition(name="Regularity",description="Is Student Regular Here",type=AttributeType.BOOLEAN)
    public boolean getRegular() default true;

    @AttributeDefinition(name="Subjects",description = "Enter Subjects",type=AttributeType.STRING)
    public String[] getSubjects() default{"Maths","English","Hindi"};

    @AttributeDefinition(name="Country",description = "Select your countries",type=AttributeType.STRING,options = {
        @Option(label="India",value = "india"),
        @Option(label = "Russia",value = "russia"),
        @Option(label="France",value="france")
    })
    public String getCountry() default"India";

}
