package com.Project.core.services;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = OSGIConfigurationMethods.class,immediate = true)
@Designate(ocd=StudentConfiguration1.class)
public class OSGIConfigurationImpl implements OSGIConfigurationMethods{

    private String studentName;
    private int rollNumber;
    private boolean regularity;
    private String[] subjects;
    private String country;

    @Activate()
    protected void start(StudentConfiguration1 config)
    {
        studentName=config.getStudentName();
        rollNumber=config.getRollNumber();
        regularity=config.getRegular();
        subjects=config.getSubjects();
        country= config.getCountry();

    }

    @Override
    public String getStudentName() {

        return studentName;
    }

    @Override
    public int getRollNumber() {
        
        return rollNumber;  
      }

    @Override
    public boolean getRegular() {
        
        return regularity;   
     }

    @Override
    public String[] getSubjects() {
      
        return subjects;
    }

    @Override
    public String getCountry() {
     
        return country;
    }


}
