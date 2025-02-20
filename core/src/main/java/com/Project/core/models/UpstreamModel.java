package com.Project.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UpstreamModel {

    @ValueMapValue
    private String textfield1;

    @ValueMapValue
    private String pathfield;

    @ValueMapValue
    private String textfield2;

    @ValueMapValue
    private String richtext;

    public String getTextfield1() {
        return textfield1;
    }

    public String getPathfield() {
        return pathfield;
    }

    public String getTextfield2() {
        return textfield2;
    }

    public String getRichtext() {
        return richtext;
    }
}
