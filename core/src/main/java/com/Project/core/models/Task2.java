package com.Project.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Task2 {

    @ValueMapValue
    private String textfield;

    @ChildResource
    private List<MultiFieldItem> multifield;

    public String getTextfield() {
        return textfield;
    }

    public List<MultiFieldItem> getMultifield() {
        return multifield;
    }
}
