package com.Project.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Task8 {

    @ValueMapValue
    private String textField;

    @ValueMapValue
    private String pathField;

    @ValueMapValue
    private String datePicker;

    private String message;

    public String getTextField() {
        return textField;
    }

    public String getPathField() {
        return pathField;
    }

    public String getMessage() {
        return message;
    }

    @PostConstruct
    protected void init() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date selected = sdf.parse(datePicker);
            Date today = sdf.parse(sdf.format(new Date()));

            if (selected.before(today)) {
                message = "Component Expired";
            } else {
                message = "Valid";
            }
        } catch (Exception e) {
            message = "Invalid Date";
        }
    }
}