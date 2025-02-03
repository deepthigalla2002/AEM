package com.Project.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import javax.inject.Inject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ResumeModel {

    @Inject
    private String fullName;

    @Inject
    private String email;

    @Inject
    private String phone;

    @Inject
    private String experience;

    @Inject
    private String skills;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getExperience() {
        return experience;
    }

    public String getSkills() {
        return skills;
    }
}
