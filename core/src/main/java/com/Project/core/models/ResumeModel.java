package com.Project.core.models;
// package com.example.core.models;

import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Model(adaptables = Resource.class, adapters = { ResumeModel.class,
        ComponentExporter.class }, resourceType = "/apps/Project/components/resume", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ResumeModel implements ComponentExporter {

    @ValueMapValue
    private String fullName;

    @ValueMapValue
    private String email;

    @ValueMapValue
    private String phone;

    @ValueMapValue
    private String summary;

    @ChildResource(name = "workExperience")
    private List<WorkExperience> workExperience;

    @ChildResource(name = "skills")
    private List<Skill> skills;

    @ChildResource(name = "education")
    private List<Education> education;

    @ChildResource(name = "projects")
    private List<Project> projects;

    @ChildResource(name = "certifications")
    private List<Certification> certifications;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getSummary() {
        return summary;
    }

    public List<WorkExperience> getWorkExperience() {
        return workExperience;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public List<Education> getEducation() {
        return education;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    @Override
    public String getExportedType() {
        return "your-project/components/resume";
    }

    // Nested Models for Multifield Items
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class WorkExperience {
        @ValueMapValue
        private String company;

        @ValueMapValue
        private String role;

        @ValueMapValue
        private String duration;

        public String getCompany() {
            return company;
        }

        public String getRole() {
            return role;
        }

        public String getDuration() {
            return duration;
        }
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class Skill {
        @ValueMapValue
        private String skill;

        public String getSkill() {
            return skill;
        }
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class Education {
        @ValueMapValue
        private String degree;

        @ValueMapValue
        private String institution;

        @ValueMapValue
        private String year;

        public String getDegree() {
            return degree;
        }

        public String getInstitution() {
            return institution;
        }

        public String getYear() {
            return year;
        }
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class Project {
        @ValueMapValue
        private String projectName;

        @ValueMapValue
        private String description;

        public String getProjectName() {
            return projectName;
        }

        public String getDescription() {
            return description;
        }
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class Certification {
        @ValueMapValue
        private String certificationName;

        @ValueMapValue
        private String issuedBy;

        @ValueMapValue
        private String year;

        public String getCertificationName() {
            return certificationName;
        }

        public String getIssuedBy() {
            return issuedBy;
        }

        public String getYear() {
            return year;
        }
    }
}
