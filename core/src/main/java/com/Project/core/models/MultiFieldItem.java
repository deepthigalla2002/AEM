package com.Project.core.models;

import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

public class MultiFieldItem {

    @ValueMapValue
    private String textfield;

    @ValueMapValue
    private String datepicker;

    public String getTextfield() {
        return textfield;
    }

    public String getDatepicker() {
        return datepicker;
    }
}
