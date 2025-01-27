package com.Project.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SidebarNavigationTab {

    @ValueMapValue
    private String desktopIcon;

    @ValueMapValue
    private String mobileIcon;

    public String getDesktopIcon() {
        return desktopIcon;
    }

    public String getMobileIcon() {
        return mobileIcon;
    }

    @ChildResource
    private List<SidebarNestedMultiField> nestedMulti;

    public List<SidebarNestedMultiField> getNestedMulti() {
        return nestedMulti;
    }
}
