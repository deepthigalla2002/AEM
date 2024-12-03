package com.Project.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Task3 {

    // Header Fields
    @ValueMapValue
    private String logopath;

    @ValueMapValue
    private String logomobileimage;

    @ValueMapValue
    private String logolink;

    @ValueMapValue
    private boolean checkbox;

   
    @ChildResource
    private List<HeaderLinkModel> multifield;

    // Sidebar Navigation
    @ChildResource
    private List<SidebarNavigation> multifield2;

    @ValueMapValue
    private String country;

    @ValueMapValue
    private String navigationURL;

  
    public List<HeaderLinkModel> getMultifield() {
        return multifield;
    }

    public List<SidebarNavigation> getMultifield2() {
        return multifield2;
    }

    public String getNavigationURL() {
        return navigationURL;
    }

    public String getLogopath() {
        return logopath;
    }

    public String getLogomobileimage() {
        return logomobileimage;
    }

    public String getLogolink() {
        return logolink;
    }

    public boolean getCheckbox() {
        return checkbox;
    }

    

    public String getCountry() {
        return country;
    }


}
