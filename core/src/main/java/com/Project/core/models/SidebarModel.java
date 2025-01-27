package com.Project.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SidebarModel {

  // Tab 1: Header
  @ValueMapValue
  private String logoPath;

  @ValueMapValue
  private String logoMobileImage;

  @ValueMapValue
  private String logoLink;

  @ValueMapValue
  private boolean enableSwitch;
  // Tab 4: Region
  @ValueMapValue
  private String country;
  // Tab 2: Header Links
  @ChildResource()
  private List<SidebarHeaderLink> headerLinks;

  // Tab 3 : SidebarNavigationTab
  @ChildResource
  private List<SidebarNavigationTab> sidebarNavigation;

  public String getLogoPath() {
    return logoPath;
  }

  public String getLogoMobileImage() {
    return logoMobileImage;
  }

  public String getLogoLink() {
    return logoLink;
  }

  public boolean getEnableSwitch() {
    return enableSwitch;
  }

  public List<SidebarHeaderLink> getHeaderLinks() {
    return headerLinks;
  }

  public List<SidebarNavigationTab> getSidebarNavigation() {
    return sidebarNavigation;
  }

  public String getCountry() {
    return country;
  }
}
