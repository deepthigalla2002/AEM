package com.Project.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MultiFieldItem {

    @ValueMapValue
    private String pathfield;

    @ValueMapValue
    private boolean checkbox;

    @ValueMapValue
    private String dropdown;

    @ValueMapValue
    private String textfield;

    @ChildResource
    private List<NestedField> nestedTextField;

    public String getPathfield() {
        return pathfield;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public String getDropdown() {
        return dropdown;
    }

    public String getTextfield() {
        return textfield;
    }

    public List<NestedField> getNestedTextField() {
        return nestedTextField;
    }
}
