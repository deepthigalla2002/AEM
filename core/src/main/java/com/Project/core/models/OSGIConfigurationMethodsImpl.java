package com.Project.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.Project.core.services.OSGIConfigurationMethods;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class OSGIConfigurationMethodsImpl {

    @OSGiService
    OSGIConfigurationMethods osgiconfig;

    public String getStudentName()
    {
        return osgiconfig.getStudentName();

    }

    public int getRollNumber()
    {
        return osgiconfig.getRollNumber();
    }

    public boolean getRegular()
    {
        return osgiconfig.getRegular();
    }

    public String[] getSubjects()
    {
        return osgiconfig.getSubjects();
    }

    public String getCountry()
    {
       return osgiconfig.getCountry();
    }

    

}
