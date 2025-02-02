package com.Project.core.models;

import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Task2 {
  @ChildResource
  private List<CarouselMultiFieldItems> multiField;

  public List<CarouselMultiFieldItems> getMultiField() {
    return multiField;
  }
}
