package com.Project.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = {
        "Project/components/sling" })
@Exporter(name = "jackson", extensions = "json")
public class PracticeExporterModel {

    @Default(values = "Test")
    @Inject
    @Named("title")
    protected String heading;

    @Inject
    @Optional
    protected String description;

    @Inject
    @Optional
    protected String country;

    @PostConstruct
    protected void init() {
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
