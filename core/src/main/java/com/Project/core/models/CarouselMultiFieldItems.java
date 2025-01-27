package com.Project.core.models;

import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselMultiFieldItems {

    @ValueMapValue
    private String textField;
    @ValueMapValue
    private String pathField;

    public String getTextField() {
        return textField;
    }

    public String getPathField() {
        return pathField;
    }

    @ChildResource
    private List<CarouselNesedMultiFieldItems> nestedMultiField;

    public List<CarouselNesedMultiFieldItems> getNestedMultiField() {
        return nestedMultiField;
    }
}
