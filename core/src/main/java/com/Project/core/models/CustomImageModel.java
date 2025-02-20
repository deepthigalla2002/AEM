package com.Project.core.models;
// package com.myproject.core.models;

import javax.inject.Inject;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.api.resource.Resource;

@Model(adaptables = Resource.class)
public class CustomImageModel {

    @Inject
    @Optional
    private String fileReference; // Fetches image from DAM

    @Inject
    @Default(values = "1")
    private String borderWidth;

    @Inject
    @Default(values = "solid")
    private String borderStyle;

    @Inject
    @Default(values = "#000000")
    private String borderColor;

    @Inject
    @Default(values = "0")
    private String borderRadius;

    @Inject
    @Default(values = "100")
    private String imageWidth;

    public String getImage() {
        return fileReference; // Returns DAM image path
    }

    public String getBorderWidth() {
        return borderWidth + "px";
    }

    public String getBorderStyle() {
        return borderStyle;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public String getBorderRadius() {
        return borderRadius + "px";
    }

    public String getImageWidth() {
        return imageWidth + "%";
    }
}
