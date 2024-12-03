package com.Project.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DialogModel {
    @ValueMapValue
    private String textField;

    @ValueMapValue
    private String fileUpload;

    @ValueMapValue
    private boolean checkbox;

    @ValueMapValue
    private String dropdown;


    // Getters
    public String getTextField() {
        return textField;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public String getDropdown() {
        return dropdown;
    }
    
}
