package com.Project.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SingleArticle {

  @ValueMapValue
  private boolean enablePii;
  @ValueMapValue
  private String cssClass;

  public boolean getEnablePii() {
    return enablePii;
  }

  public String getCssClass() {
    return cssClass;
  }
}
