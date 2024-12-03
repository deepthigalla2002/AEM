package com.Project.core.models;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.*;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class examplecomponent {

    @ValueMapValue
    private String textfield;

    @ValueMapValue
    private String pathfield;

    public String getTextfield() {
        return textfield;
    }

    public String getPathfield() {
        return pathfield;
    }

}
