package com.Project.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.Model;

import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Task1 {

    @ValueMapValue
    private String textfield;

    @ValueMapValue
    private String pathfield;

    @ValueMapValue
    private boolean checkbox;

    @ChildResource
    private List<MultiFieldItem> multifield;

    public String getTextfield() {
        return textfield;
    }

    public String getPathfield() {
        return pathfield;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public List<MultiFieldItem> getMultifield() {
        return multifield;
    }

    

}
