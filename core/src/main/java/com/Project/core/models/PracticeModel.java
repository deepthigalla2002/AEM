package com.Project.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.Project.core.services.PracticeService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = Resource.class)
public class PracticeModel {

    @Default(values = "Test")
    @Inject
    @Optional
    @Named("title")
    protected String heading;

    @OSGiService
    PracticeService practiceOCDService;

    private String name;

    @PostConstruct
    protected void init() {
        name = practiceOCDService.getName();
    }

    public String getName() {
        return name;
    }

    public String getHeading() {
        return heading;
    }
}
